package uz.pdp.uzummarket.service.bucketService;

import uz.pdp.uzummarket.Dto.requestSTO.BasketDTO;
import uz.pdp.uzummarket.Dto.requestSTO.CategoryDTO;
import uz.pdp.uzummarket.entity.Category;

import java.util.List;
import java.util.UUID;

public interface BasketService {
    BasketDTO getById(UUID basketId);
    List<BasketDTO> getAll(Long page, Long size);
}
