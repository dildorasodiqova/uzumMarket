package uz.pdp.uzummarket.service.productPhotosService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
import uz.pdp.uzummarket.entity.ProductPhotos;
import uz.pdp.uzummarket.repository.ProductPhotosRepository;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ProductPhotosServiceImpl implements ProductPhotosService{
    private final ProductPhotosRepository productPhotosRepository;
    @Override
    public BaseResponse<List<ProductPhotos>> getByProductId(UUID productId) {
        return BaseResponse.<List<ProductPhotos>>builder()
                .code(200)
                .message("success")
                .data(productPhotosRepository.getByProduct_Id(productId))
                .success(true)
                .build();
    }

    @Override
    public ProductPhotos save(ProductPhotos productPhotos) {
        return productPhotosRepository.save(productPhotos);
    }

}
