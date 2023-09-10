package uz.pdp.uzummarket.service.bucketService;

import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
import uz.pdp.uzummarket.Dto.responceDTO.BasketResponseDTO;

import java.util.List;
import java.util.UUID;

public interface BasketService {
    BasketResponseDTO getById(UUID basketId);
    List<BasketResponseDTO> getAll(Long page, Long size);
    BaseResponse<BasketResponseDTO> create(UUID userId, UUID productId, int count);
    BaseResponse<BasketResponseDTO> getUserProduct(UUID userId);

    BaseResponse<BasketResponseDTO> updateProduct(UUID productId, UUID basketId, int count);
}
