package uz.pdp.uzummarket.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.uzummarket.Dto.requestSTO.CategoryDTO;
import uz.pdp.uzummarket.service.categoryService.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDTO>> getAll(@RequestParam(defaultValue = "0") Long page, @RequestParam(defaultValue = "10") Long size){
         return ResponseEntity.ok(categoryService.getAll(page,size));
    }

}
