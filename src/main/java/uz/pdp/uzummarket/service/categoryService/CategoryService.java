package uz.pdp.uzummarket.service.categoryService;

import uz.pdp.uzummarket.entity.Category;

import java.util.UUID;

public interface CategoryService {
    Category getById(UUID categoryId);

}
