package uz.pdp.uzummarket.service.bucketService;
import uz.pdp.uzummarket.Dto.responceDTO.BasketResponseDTO;
import uz.pdp.uzummarket.entity.Category;

import java.util.List;
import java.util.UUID;

public interface BasketService {
    BasketResponseDTO getById(UUID basketId);
    List<BasketResponseDTO> getAll(Long page, Long size);
    BasketResponseDTO create(UUID userId, UUID productId, int count);
}
