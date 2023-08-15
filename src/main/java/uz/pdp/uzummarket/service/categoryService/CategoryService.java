package uz.pdp.uzummarket.service.categoryService;

import uz.pdp.uzummarket.Dto.requestSTO.CategoryDTO;
import uz.pdp.uzummarket.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    Category getById(UUID categoryId);
    List<CategoryDTO> getAll(Long page, Long size);

}
