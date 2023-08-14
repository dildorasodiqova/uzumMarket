package uz.pdp.uzummarket.service.productService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.Dto.requestSTO.ProductRequestDto;
import uz.pdp.uzummarket.Dto.responceDTO.ProductResponseDto;
import uz.pdp.uzummarket.entity.Category;
import uz.pdp.uzummarket.entity.Product;
import uz.pdp.uzummarket.repository.ProductRepository;
import uz.pdp.uzummarket.service.categoryService.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    @Override
    public ProductResponseDto save(ProductRequestDto dto) {
        Product product = createProduct(dto);
        return createProductResponseDto(productRepository.save(product));
    }

    @Override
    public Page<ProductResponseDto> getAll(int size, int page) {
        Page<Product> all = productRepository.findAll(PageRequest.of(page, size));
        List<ProductResponseDto> responseDtos = new ArrayList<>();
        for (Product product : all.getContent()) {
            responseDtos.add(createProductResponseDto(product));
        }
        return new PageImpl<>(responseDtos);
    }

    @Override
    public ProductResponseDto update(UUID productId, ProductRequestDto dto) {
        Product product = createProduct(dto);
        product.setId(productId);
        return createProductResponseDto(productRepository.save(product));
    }

    private Product createProduct(ProductRequestDto dto) {
        Category category = categoryService.getById(dto.getCategoryId());

        return Product.builder()
                .price(dto.getPrice())
                .name(dto.getName())
                .category(category)
                .description(dto.getDescription())
                .count(dto.getCount())
                .build();
    }

    private ProductResponseDto createProductResponseDto(Product product) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setDescription(product.getDescription());
        responseDto.setName(product.getName());
        responseDto.setPrice(product.getPrice());
        responseDto.setCount(product.getCount());
        responseDto.setCategoryId(product.getCategory().getId());
        return responseDto;
    }
}
