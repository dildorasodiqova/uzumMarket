package uz.pdp.uzummarket.service.productService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.Dto.requestSTO.ProductCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.ProductResponseDTO;
import uz.pdp.uzummarket.entity.Category;
import uz.pdp.uzummarket.entity.Product;
import uz.pdp.uzummarket.entity.ProductPhotos;
import uz.pdp.uzummarket.exception.DataNotFoundException;
import uz.pdp.uzummarket.repository.ProductRepository;
import uz.pdp.uzummarket.service.attachmentService.AttachmentService;
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
    private final AttachmentService attachmentService;
    private final ProductPhotosService productPhotosService;

    @Override
    public ProductResponseDTO save(ProductCreateDTO dto) {
        ProductResponseDTO product = createProduct(dto);
        return product;
    }


    @Override
    public Page<ProductResponseDTO> getAll(int size, int page) {
        if (size <= 0 &&  page <= 0){
            List<Product> all = productRepository.findAll();
            List<ProductResponseDTO> parse = parse(all);
            return new  PageImpl<>(parse);
        }
        Page<Product> all = productRepository.findAll(PageRequest.of(page, size));
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
        List<Product> products = productRepository.searchProductByCategory_NameOrNameContainingIgnoreCase(word);
        return parse(products);
    }


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

    @Override
    public Product getById(UUID productId) {
        Optional<Product> byId = productRepository.findById(productId);
        return byId.get();
    }

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

        List<ProductPhotos> byProductId = productPhotosService.getByProductId(save.getId());

        ProductResponseDTO map1 = modelMapper.map(save, ProductResponseDTO.class);
        map1.setId(save.getId());
        map1.setCategoryId(save.getCategory().getId());
        List<UUID> photosId = new ArrayList<>();
        for (ProductPhotos productPhotos : byProductId) {
            photosId.add(productPhotos.getPhoto().getId());
        }
        map1.setPhotos(photosId);
        return map1;
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
