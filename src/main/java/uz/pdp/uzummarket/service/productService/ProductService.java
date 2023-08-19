package uz.pdp.uzummarket.service.productService;

import org.springframework.data.domain.Page;
import uz.pdp.uzummarket.Dto.requestSTO.ProductCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.ProductResponseDTO;
import uz.pdp.uzummarket.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponseDTO save(ProductCreateDTO dto);

    Page<ProductResponseDTO> getAll(UUID sellerId,int size, int page);

    List<ProductResponseDTO> search(String word);

    ProductResponseDTO update(UUID productId, ProductCreateDTO dto);
    ProductResponseDTO findById(UUID productId);
    Product getById(UUID productId);
    String delete(UUID productId);
}
