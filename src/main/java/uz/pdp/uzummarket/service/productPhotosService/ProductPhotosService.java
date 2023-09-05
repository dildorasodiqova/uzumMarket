package uz.pdp.uzummarket.service.productPhotosService;

import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
import uz.pdp.uzummarket.entity.ProductPhotos;

import java.util.List;
import java.util.UUID;

public interface ProductPhotosService {
    BaseResponse<List<ProductPhotos>> getByProductId(UUID productId);

    ProductPhotos save(ProductPhotos productPhotos);
}
