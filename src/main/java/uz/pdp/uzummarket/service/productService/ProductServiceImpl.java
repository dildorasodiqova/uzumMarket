package uz.pdp.uzummarket.service.productService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.uzummarket.Dto.requestSTO.ProductCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.ProductResponseDTO;
import uz.pdp.uzummarket.entity.Attachment;
import uz.pdp.uzummarket.entity.Category;
import uz.pdp.uzummarket.entity.Product;
import uz.pdp.uzummarket.entity.ProductPhotos;
import uz.pdp.uzummarket.exception.DataNotFoundException;
import uz.pdp.uzummarket.repository.AttachmentRepository;
import uz.pdp.uzummarket.repository.ProductPhotosRepository;
import uz.pdp.uzummarket.repository.ProductRepository;
import uz.pdp.uzummarket.service.categoryService.CategoryService;
import uz.pdp.uzummarket.service.productPhotosService.ProductPhotosService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final ProductPhotosRepository productPhotosRepository;
    private final ProductPhotosService productPhotosService;
    private final AttachmentRepository attachmentRepository;
@Transactional
    @Override
    public ProductResponseDTO save(ProductCreateDTO dto) {
        ProductResponseDTO product = createProduct(dto);
        return product;
    }


    @Override
    public Page<ProductResponseDTO> getAll( UUID sellerId ,int size, int page) {
        if (size <= 0 && page <= 0) {
            Page<Product> all = productRepository.findAllByUserId(sellerId,PageRequest.of(page, size));
            List<ProductResponseDTO> parse = parse(all);
            return new PageImpl<>(parse);
        }
        Page<Product> all = productRepository.findAll(PageRequest.of(page, size));
//    public Page<ProductResponseDTO > getAll(UUID sellerId,int size, int page) {
        Page<Product> all = productRepository.findAllByUserId(sellerId,PageRequest.of(page, size));
        List<ProductResponseDTO> responseDtos = new ArrayList<>();
        for (Product product : all.getContent()) {
            ProductResponseDTO map = modelMapper.map(product, ProductResponseDTO.class);
            map.setId(product.getId());

            List<ProductPhotos> byProductId = productPhotosService.getByProductId(product.getId());
            List<UUID> uuids = new ArrayList<>();
            for (ProductPhotos productPhotos : byProductId) {
                uuids.add(productPhotos.getId());
            }
            map.setPhotos(uuids);
            responseDtos.add(map);

        }
        return new PageImpl<>(responseDtos);
    }

    @Override
    public List<ProductResponseDTO> search(String word) {
        List<Product> products = productRepository.searchProductByCategory_NameOrNameContainingIgnoreCase(word, word);
        return parse(products);
    }

@Transactional
    @Override
    public ProductResponseDTO update(UUID productId, ProductCreateDTO dto) {
        Optional<Product> byId = productRepository.findById(productId);
        Product product = byId.get();
        product.setPrice(dto.getPrice());
        product.setCount(dto.getCount());
        product.setDescription(dto.getDescription());
        productRepository.save(product);
        return modelMapper.map(product, ProductResponseDTO.class);
    }

    @Override
    public ProductResponseDTO findById(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found!"));
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        List<ProductPhotos> byProductId = productPhotosService.getByProductId(product.getId());
        List<UUID> photosId = getPhotosId(byProductId);

        productResponseDTO.setPhotos(photosId);
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setCount(product.getCount());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setCategoryId(product.getCategory().getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setId(product.getId());

        return productResponseDTO;
    }
@Transactional
    @Override
    public Product getById(UUID productId) {
        Optional<Product> byId = productRepository.findById(productId);
        return byId.get();
    }
@Transactional
    public List<UUID> getPhotosId(List<ProductPhotos> productPhotos) {
        List<UUID> list = new ArrayList<>();
        for (ProductPhotos productPhoto : productPhotos) {
            list.add(productPhoto.getId());
        }
        return list;
    }

    @Override
    public String delete(UUID productId) {
        productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found"));
        productRepository.deleteById(productId);
        return "Successfully";
    }
@Transactional
    private ProductResponseDTO createProduct(ProductCreateDTO dto) {
        Category byId = categoryService.getByIdCategory(dto.getCategoryId());
        Product product = Product.builder()
                .category(byId)
                .count(dto.getCount())
                .description(dto.getDescription())
                .name(dto.getName())
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
            list.add(productPhotos);
        }

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setCount(save.getCount());
        productResponseDTO.setDescription(save.getDescription());
        productResponseDTO.setPrice(save.getPrice());
        productResponseDTO.setName(save.getName());
        productResponseDTO.setId(save.getId());
        productResponseDTO.setPhotos(dto.getPhotos());
        productResponseDTO.setCategoryId(save.getCategory().getId());
        return productResponseDTO;
    }

    public List<ProductResponseDTO> parse(List<Product> product) {
        ProductResponseDTO responseDto = new ProductResponseDTO();
        List<ProductResponseDTO> list = new ArrayList<>();
        for (Product product1 : product) {
            responseDto.setDescription(product1.getDescription());
            responseDto.setName(product1.getName());
            responseDto.setPrice(product1.getPrice());
            responseDto.setCount(product1.getCount());
            responseDto.setId(product1.getId());

            List<UUID> photosId = getPhotosId(productPhotosService.getByProductId(product1.getId()));

            responseDto.setPhotos(photosId);
            responseDto.setCategoryId(product1.getCategory().getId());
            list.add(responseDto);
        }
        return list;
    }


}
