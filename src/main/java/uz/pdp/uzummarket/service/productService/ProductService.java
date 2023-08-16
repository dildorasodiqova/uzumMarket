package uz.pdp.uzummarket.service.productService;

import org.springframework.data.domain.Page;
import uz.pdp.uzummarket.Dto.requestSTO.ProductCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.ProductResponseDTO;
import java.util.UUID;

public interface ProductService {
    ProductResponseDTO save(ProductCreateDTO dto);

    Page<ProductResponseDTO> getAll(int size, int page);

    ProductResponseDTO update(UUID productId, ProductCreateDTO dto);
}
