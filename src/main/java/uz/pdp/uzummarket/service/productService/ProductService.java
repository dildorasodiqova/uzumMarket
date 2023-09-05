package uz.pdp.uzummarket.service.productService;

import org.springframework.data.domain.Page;
import uz.pdp.uzummarket.Dto.requestSTO.ProductCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
import uz.pdp.uzummarket.Dto.responceDTO.ProductResponseDTO;
import uz.pdp.uzummarket.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    BaseResponse<ProductResponseDTO> save(ProductCreateDTO dto);

    BaseResponse<List<ProductResponseDTO>> getAll(UUID sellerId,UUID categoryId, int size, int page);

    BaseResponse<List<ProductResponseDTO>> search(String word);

    BaseResponse<ProductResponseDTO> update(UUID productId, ProductCreateDTO dto);
    BaseResponse<ProductResponseDTO> findById(UUID productId);
    BaseResponse<Product> getById(UUID productId);
    BaseResponse<String> delete (UUID productId);

    BaseResponse<List<ProductResponseDTO>> getByCategory(UUID sellerId, UUID categoryId);
}
