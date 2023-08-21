package uz.pdp.uzummarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.uzummarket.Dto.requestSTO.ProductCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
import uz.pdp.uzummarket.Dto.responceDTO.ProductResponseDTO;
import uz.pdp.uzummarket.service.productService.ProductService;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<ProductResponseDTO>> create
            (
                    @RequestBody ProductCreateDTO dto
            ) {
        return ResponseEntity.ok(productService.save(dto));
    }


    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.delete(productId));
    }
    @GetMapping("/getById/{productId}")
    public ResponseEntity<BaseResponse<ProductResponseDTO>> getById(@PathVariable UUID productId){
        return ResponseEntity.ok(productService.findById(productId));
    }
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<Page<ProductResponseDTO>>> search(@RequestParam String word){
        return ResponseEntity.ok(productService.search(word));
    }
}
