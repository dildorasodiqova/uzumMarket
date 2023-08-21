package uz.pdp.uzummarket.service.categoryService;

import uz.pdp.uzummarket.Dto.requestSTO.CategoryCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
import uz.pdp.uzummarket.Dto.responceDTO.CategoryResponseDTO;
import uz.pdp.uzummarket.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    BaseResponse<CategoryResponseDTO> getById(UUID categoryId);
    BaseResponse<List<CategoryResponseDTO>> getAll(Long page, Long size);

    BaseResponse<CategoryResponseDTO> create(CategoryCreateDTO createDTO);
    BaseResponse<Category> getByIdCategory(UUID categoryId);

    BaseResponse<List<CategoryResponseDTO>> firstCategories();
    BaseResponse<List<CategoryResponseDTO>> subCategories(UUID parentId);

}
