package uz.pdp.uzummarket.service.productService;

import org.springframework.data.domain.Page;
import uz.pdp.uzummarket.Dto.requestSTO.ProductRequestDto;
import uz.pdp.uzummarket.Dto.responceDTO.ProductResponseDto;

import java.util.UUID;

public interface ProductService {
    ProductResponseDto save(ProductRequestDto dto);

    Page<ProductResponseDto> getAll(int size, int page);

    ProductResponseDto update(UUID productId, ProductRequestDto dto);
}
