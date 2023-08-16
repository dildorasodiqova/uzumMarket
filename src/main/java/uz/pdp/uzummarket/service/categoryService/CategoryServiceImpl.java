package uz.pdp.uzummarket.service.categoryService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.Dto.requestSTO.CategoryCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.CategoryResponseDTO;
import uz.pdp.uzummarket.entity.Attachment;
import uz.pdp.uzummarket.entity.Category;
import uz.pdp.uzummarket.exception.DataNotFoundException;
import uz.pdp.uzummarket.repository.AttachmentRepository;
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
    private final AttachmentRepository attachmentRepository;
    @Override
    public CategoryResponseDTO getById(UUID categoryId) {
        Optional<Category> categoryBy = categoryRepository.getCategoryById(categoryId);
        if (categoryBy == null){
            throw new DataNotFoundException("Category not found");
        }else {
            CategoryResponseDTO map = modelMapper.map(categoryBy, CategoryResponseDTO.class);
            return map;
        }
    }

    public List<CategoryResponseDTO> getAll(Long page, Long size){
        Page<Category> all = categoryRepository.findAll(PageRequest.of(page.intValue(), size.intValue()));
        List<CategoryResponseDTO> categoryDTOS = new ArrayList<>();
        for (Category category : all) {
            CategoryResponseDTO map = modelMapper.map(category, CategoryResponseDTO.class);
            categoryDTOS.add(map);
        }
        return  categoryDTOS;
    }

    @Override
    public CategoryResponseDTO create(CategoryCreateDTO createDTO) {
        Category parentCategory = null;
        if (createDTO.getParentId() != null) {
            parentCategory = categoryRepository.findById(createDTO.getParentId()).orElseThrow();
        }

        Attachment photo = null;
        if (createDTO.getPhotoId() != null) {
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(createDTO.getPhotoId());
            photo = optionalAttachment.orElseThrow();
        }

        Category category = Category.builder()
                .setName(createDTO.getName())
                .setActive(createDTO.isActive())
                .setParent(parentCategory)
                .setPhoto(photo)
                .build();

        categoryRepository.save(category);

        CategoryResponseDTO response = modelMapper.map(category, CategoryResponseDTO.class);
        response.setParentId(parentCategory != null ? parentCategory.getId() : null);
        response.setPhotoId(photo != null ? photo.getId() : null);

        return response;
    }

}
