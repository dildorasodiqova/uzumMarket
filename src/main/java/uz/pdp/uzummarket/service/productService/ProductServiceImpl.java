package uz.pdp.uzummarket.service.productService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.postgresql.util.PSQLException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.uzummarket.Dto.requestSTO.ProductCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
import uz.pdp.uzummarket.Dto.responceDTO.ProductResponseDTO;
import uz.pdp.uzummarket.entity.Attachment;
import uz.pdp.uzummarket.entity.Category;
import uz.pdp.uzummarket.entity.Product;
import uz.pdp.uzummarket.entity.ProductPhotos;
import uz.pdp.uzummarket.exception.DataAlreadyExistsException;
import uz.pdp.uzummarket.exception.DataNotFoundException;
import uz.pdp.uzummarket.repository.AttachmentRepository;
import uz.pdp.uzummarket.repository.ProductPhotosRepository;
import uz.pdp.uzummarket.repository.ProductRepository;
import uz.pdp.uzummarket.repository.UserRepository;
import uz.pdp.uzummarket.service.categoryService.CategoryService;
import uz.pdp.uzummarket.service.productPhotosService.ProductPhotosService;
import uz.pdp.uzummarket.service.userService.UserService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final ProductPhotosRepository productPhotosRepository;
    private final ProductPhotosService productPhotosService;
    private final AttachmentRepository attachmentRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public BaseResponse save(ProductCreateDTO dto) {
        ProductResponseDTO product = null;
        try {
            product = createProduct(dto);
        } catch (PSQLException e) {
            throw new DataNotFoundException("Product already exists");
        }
        return BaseResponse.<ProductResponseDTO>builder()
                .code(200)
                .message("success")
                .success(true)
                .data(product)
                .build();
    }


    @Override
    public BaseResponse<List<ProductResponseDTO>> getAll(UUID userId, UUID categoryId, int size, int page) {

        List<Product> all = productRepository.getAllByUserIdAndCategory_id(userId, categoryId);
        List<ProductResponseDTO> parse = parse(all);
        return BaseResponse.<List<ProductResponseDTO>>builder()
                .data(parse)
                .message("success")
                .success(true)
                .code(200)
                .build();
    }

    @Override
    public BaseResponse<List<ProductResponseDTO>> search(String word) {
        List<Product> products = productRepository.searchProductByNameOrCategoryName(word);
        List<ProductResponseDTO> parse = parse(products);
        return BaseResponse.<List<ProductResponseDTO>>builder()
                .data(parse)
                .success(true)
                .message("success")
                .code(200)
                .build();
    }

    @Transactional
    @Override
    public BaseResponse<ProductResponseDTO> update(UUID productId, ProductCreateDTO dto) {
        Optional<Product> byId = productRepository.findById(productId);
        Product product = byId.get();
        product.setPrice(dto.getPrice());
        product.setCount(dto.getCount());
        product.setDescription(dto.getDescription());
        productRepository.save(product);
        return BaseResponse.<ProductResponseDTO>builder()
                .data(modelMapper.map(product, ProductResponseDTO.class))
                .success(true)
                .message("success")
                .code(200)
                .build();
    }

    @Override
    public BaseResponse<ProductResponseDTO> findById(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found!"));
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        BaseResponse<List<ProductPhotos>> photos = productPhotosService.getByProductId(product.getId());
        List<UUID> photosId = getPhotosId(photos.getData());

        productResponseDTO.setPhotos(photosId);
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setCount(product.getCount());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setCategoryId(product.getCategory().getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setId(product.getId());

        return BaseResponse.<ProductResponseDTO>builder()
                .data(productResponseDTO)
                .message("success")
                .code(200)
                .success(true)
                .build();
    }

    @Transactional
    @Override
    public BaseResponse<Product> getById(UUID productId) {
        Optional<Product> byId = productRepository.findById(productId);
        return BaseResponse.<Product>builder()
                .message("success")
                .success(true)
                .code(200)
                .data(byId.get())
                .build();
    }

    @Transactional
    public List<UUID> getPhotosId(List<ProductPhotos> productPhotos) {
        List<UUID> list = new ArrayList<>();
        for (ProductPhotos productPhoto : productPhotos) {
            list.add(productPhoto.getPhoto().getId());
        }
        return list;
    }

    @Override
    public BaseResponse<String> delete(UUID productId) {
        productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found"));
        productRepository.deleteById(productId);
        return BaseResponse.<String>builder()
                .data("Successfully")
                .success(true)
                .message("success")
                .code(200)
                .build();
    }

    @Override
    public BaseResponse<List<ProductResponseDTO>> getByCategory(UUID categoryId) {
        return BaseResponse.<List<ProductResponseDTO>>builder()
                .data(parse(productRepository.getProductsByCategory_Id(categoryId)))
                .success(true)
                .message("success")
                .code(200)
                .build();
    }

    @Transactional
    public ProductResponseDTO createProduct(ProductCreateDTO dto) throws PSQLException {
        System.out.println("dto = " + dto);
        if (productRepository.findByName(dto.getName()).isPresent()) {
            throw new DataAlreadyExistsException("Product already exists");
        }
        BaseResponse<Category> id = categoryService.getByIdCategory(dto.getCategoryId());
        Product product = Product.builder()
                .category(id.getData())
                .count(dto.getCount())
                .description(dto.getDescription())
                .name(dto.getName())
                .user(userRepository.findById(dto.getSellerId()).get())
                .price(dto.getPrice())
                .build();

        Product save = productRepository.save(product);

        List<Attachment> photos = attachmentRepository.findAllById(dto.getPhotos());
        List<ProductPhotos> list = new ArrayList<>();
        for (int i = 0; i < photos.size(); i++) {
            ProductPhotos productPhotos = new ProductPhotos();
            productPhotos.setProduct(save);
            productPhotos.setPhoto(photos.get(i));
            productPhotos.setOrderIndex(i);
            productPhotosService.save(productPhotos);
            list.add(productPhotos);
        }

        List<UUID> photosId = getPhotosId(list);

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setCount(save.getCount());
        productResponseDTO.setDescription(save.getDescription());
        productResponseDTO.setPrice(save.getPrice());
        productResponseDTO.setName(save.getName());
        productResponseDTO.setId(save.getId());
        productResponseDTO.setPhotos(photosId);
        productResponseDTO.setCategoryId(save.getCategory().getId());
        return productResponseDTO;
    }

    public List<ProductResponseDTO> parse(List<Product> products) {
/*List<Product> all = productRepository.getAllByUserIdAndCategory_id(userId, categoryId);
        List<ProductResponseDTO> responseDtos = new ArrayList<>();
        for (Product product : all) {
            ProductResponseDTO map = modelMapper.map(product, ProductResponseDTO.class);
            map.setId(product.getId());

            BaseResponse<List<ProductPhotos>> photos = productPhotosService.getByProductId(product.getId());
            List<UUID> uuids = new ArrayList<>();
            for (ProductPhotos productPhotos : photos.getData()) {
                uuids.add(productPhotos.getPhoto().getId());
            }
            map.setPhotos(uuids);
            responseDtos.add(map);
        }
     **/
        List<ProductResponseDTO> list = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDTO map = modelMapper.map(product,ProductResponseDTO.class);
            map.setId(product.getId());
            BaseResponse<List<ProductPhotos>> byProductId = productPhotosService.getByProductId(product.getId());
            List<UUID> photosId = getPhotosId(byProductId.getData());
            map.setPhotos(photosId);
            list.add(map);
        }
        return list;
    }


}
