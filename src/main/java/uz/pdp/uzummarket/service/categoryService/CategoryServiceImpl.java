package uz.pdp.uzummarket.service.categoryService;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.Dto.requestSTO.CategoryDTO;
import uz.pdp.uzummarket.entity.Category;
import uz.pdp.uzummarket.entity.Product;
import uz.pdp.uzummarket.exception.DataNotFoundException;
import uz.pdp.uzummarket.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryDTO create(CategoryDTO categoryDTO){
return null;
    }
    @Override
    public Category getById(UUID categoryId) {
        Optional<Category> categoryBy = categoryRepository.getCategoryBy(categoryId);
        return categoryBy.orElseThrow(() -> new DataNotFoundException("Category not found"));
    }

    public List<CategoryDTO> getAll(Long page, Long size){
        Page<Category> all = categoryRepository.findAll(PageRequest.of(page.intValue(), size.intValue()));
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category category : all) {
            CategoryDTO map = modelMapper.map(category, CategoryDTO.class);
            categoryDTOS.add(map);
        }
        return  categoryDTOS;
    }

}
