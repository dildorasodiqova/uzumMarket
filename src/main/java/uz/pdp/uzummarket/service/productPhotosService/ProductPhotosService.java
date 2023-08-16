package uz.pdp.uzummarket.service.productPhotosService;

import uz.pdp.uzummarket.entity.ProductPhotos;

import java.util.List;
import java.util.UUID;

public interface ProductPhotosService {
    List<ProductPhotos> getByProductId(UUID productId);
}
