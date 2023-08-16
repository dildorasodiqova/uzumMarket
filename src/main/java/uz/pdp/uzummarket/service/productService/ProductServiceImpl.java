package uz.pdp.uzummarket.service.productService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.Dto.requestSTO.ProductCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.CategoryResponseDTO;
import uz.pdp.uzummarket.Dto.responceDTO.ProductResponseDTO;
import uz.pdp.uzummarket.entity.Category;
import uz.pdp.uzummarket.entity.Product;
import uz.pdp.uzummarket.entity.ProductPhotos;
import uz.pdp.uzummarket.repository.AttachmentRepository;
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
    private final AttachmentRepository attachmentRepository;
    private final ProductPhotosService productPhotosService;
    @Override
    public ProductResponseDTO save(ProductCreateDTO dto) {
        ProductResponseDTO product = createProduct(dto);
        return product;
    }

    @Override
    public Page<ProductResponseDTO > getAll(int size, int page) {
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
    public ProductResponseDTO update(UUID productId, ProductCreateDTO dto) {
        Optional<Product> byId = productRepository.findById(productId);
        Product product = byId.get();
        product.setPrice(dto.getPrice());
        product.setCount(dto.getCount());
        product.setDescription(dto.getDescription());
        return modelMapper.map(product,ProductResponseDTO.class);
    }

    private ProductResponseDTO createProduct(ProductCreateDTO dto) {
         Product map = modelMapper.map(dto, Product.class);
        Product save = productRepository.save(map);

        List<ProductPhotos> byProductId = productPhotosService.getByProductId(save.getId());

        ProductResponseDTO map1 = modelMapper.map(save, ProductResponseDTO.class);
        map1.setId(save.getId());
        List<UUID> photosId = new ArrayList<>();
        for (ProductPhotos productPhotos : byProductId) {
            photosId.add(productPhotos.getPhoto().getId());
        }
        map1.setPhotos(photosId);
        return map1;
    }

//    private ProductResponseDTO createProductResponseDto(Product product) {
//        ProductResponseDTO responseDto = new ProductResponseDTO();
//        responseDto.setDescription(product.getDescription());
//        responseDto.setName(product.getName());
//        responseDto.setPrice(product.getPrice());
//        responseDto.setCount(product.getCount());
//        responseDto.setId(product.getId());
//        responseDto.setPhotos();
//        responseDto.setCategoryId(product.getCategory().getId());
//        return responseDto;
//    }
}
