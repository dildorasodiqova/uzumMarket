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
import uz.pdp.uzummarket.exception.DataAlreadyExistsException;
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
        if (categoryBy.isEmpty()){
            throw new DataNotFoundException("Category not found");
        }else {
            Category category = categoryBy.get();
            CategoryResponseDTO responseDTO = new CategoryResponseDTO();
            responseDTO.setParentId(category.getParent().getId());
            responseDTO.setActive(category.isActive());
            responseDTO.setName(category.getName());
            responseDTO.setPhotoId(category.getPhoto().getId());
            return responseDTO;
        }
    }
@Override
    public List<CategoryResponseDTO> getAll(Long page, Long size){
        Page<Category> all = categoryRepository.findAll(PageRequest.of(page.intValue(), size.intValue()));
        List<CategoryResponseDTO> categoryDTOS = new ArrayList<>();
        for (Category category : all.getContent()) {
            CategoryResponseDTO map = modelMapper.map(category, CategoryResponseDTO.class);
            categoryDTOS.add(map);
        }
        return  categoryDTOS;
    }




    @Override
    public CategoryResponseDTO create(CategoryCreateDTO createDTO) {
        Optional<Category> byName = categoryRepository.getByName(createDTO.getName());
        if (byName.isPresent()){
            throw new DataAlreadyExistsException("Category name already exists");
        }
        Category parentCategory = null;
        if (createDTO.getParentId() != null) {
            parentCategory = categoryRepository.findById(createDTO.getParentId()).orElseThrow(() -> new DataNotFoundException("Category not found"));
        }

        Attachment photo = null;
        if (createDTO.getPhotoId() != null) {
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(createDTO.getPhotoId());
            photo = optionalAttachment.orElseThrow(()->new DataNotFoundException("Photo not found!"));
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

    @Override
    public Category getByIdCategory(UUID categoryId) {
        return categoryRepository.getCategoryById(categoryId).orElseThrow(()->new DataNotFoundException("Category not found"));
    }

    @Override
    public List<CategoryResponseDTO> firstCategories() {
        List<Category> categories = categoryRepository.getCategoriesByParent_Id();
        List<CategoryResponseDTO> list = new ArrayList<>();
        for (Category category : categories) {
            if (category.isActive()) {
                CategoryResponseDTO responseDTO = new CategoryResponseDTO();
                responseDTO.setActive(category.isActive());
                responseDTO.setName(category.getName());
                responseDTO.setId(category.getId());
                responseDTO.setPhotoId(category.getPhoto().getId());
                responseDTO.setParentId(null);

            list.add(responseDTO);
            }
        }
        return list;
    }

    @Override
    public List<CategoryResponseDTO> subCategories(UUID parentId) {
        List<Category> categoriesByParentId = categoryRepository.getCategoriesByParent_Id(parentId);
        List<CategoryResponseDTO> parse = parse(categoriesByParentId);
        return parse;
    }

    public List<CategoryResponseDTO> parse(List<Category> category){
        List<CategoryResponseDTO> list = new ArrayList<>();
        for (Category category1 : category) {
            if (category1.isActive()) {
                CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
                categoryResponseDTO.setPhotoId(category1.getPhoto().getId());
                categoryResponseDTO.setId(category1.getId());
                categoryResponseDTO.setName(category1.getName());
                categoryResponseDTO.setActive(category1.isActive());
                categoryResponseDTO.setParentId(category1.getParent().getId());
                list.add(categoryResponseDTO);
            }
        }
        return list;
    }

}
