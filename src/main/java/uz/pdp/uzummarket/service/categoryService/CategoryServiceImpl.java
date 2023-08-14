package uz.pdp.uzummarket.service.categoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.entity.Category;
import uz.pdp.uzummarket.exception.DataNotFoundException;
import uz.pdp.uzummarket.repository.CategoryRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    @Override
    public Category getById(UUID categoryId) {
        Optional<Category> categoryBy = categoryRepository.getCategoryBy(categoryId);
        return categoryBy.orElseThrow(() -> new DataNotFoundException("Category not found"));
    }
}
