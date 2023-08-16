package uz.pdp.uzummarket.service.categoryService;

import uz.pdp.uzummarket.Dto.requestSTO.CategoryCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.CategoryResponseDTO;
import uz.pdp.uzummarket.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponseDTO getById(UUID categoryId);
    List<CategoryResponseDTO> getAll(Long page, Long size);

    CategoryResponseDTO create(CategoryCreateDTO createDTO);

}
