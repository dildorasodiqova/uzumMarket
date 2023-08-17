package uz.pdp.uzummarket.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.uzummarket.Dto.requestSTO.CategoryCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.CategoryResponseDTO;
import uz.pdp.uzummarket.service.categoryService.CategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryResponseDTO>> getAll(@RequestParam(defaultValue = "0") Long page, @RequestParam(defaultValue = "10") Long size){
         return ResponseEntity.ok(categoryService.getAll(page,size));
    }
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CategoryCreateDTO createDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(createDTO));
    }
    @GetMapping("/firstCategory")
    private ResponseEntity<List<CategoryResponseDTO>> firstCategory(){
        return ResponseEntity.ok(categoryService.firstCategories());
    }
    @GetMapping("getById/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable UUID categoryId){
        return ResponseEntity.ok(categoryService.getById(categoryId));
    }
    @GetMapping("/subCategories/{categoryId}")
    public ResponseEntity<List<CategoryResponseDTO>> getSubCategories(@PathVariable UUID categoryId){
        return ResponseEntity.ok(categoryService.subCategories(categoryId));
    }

}
