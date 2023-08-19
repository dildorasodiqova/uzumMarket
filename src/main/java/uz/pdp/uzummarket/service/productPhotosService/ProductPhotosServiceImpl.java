package uz.pdp.uzummarket.service.productPhotosService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.entity.ProductPhotos;
import uz.pdp.uzummarket.repository.ProductPhotosRepository;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ProductPhotosServiceImpl implements ProductPhotosService{
    private final ProductPhotosRepository productPhotosRepository;
    @Override
    public List<ProductPhotos> getByProductId(UUID productId) {
        return productPhotosRepository.getByProduct_Id(productId);
    }

}
