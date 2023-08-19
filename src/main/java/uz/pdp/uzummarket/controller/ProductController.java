package uz.pdp.uzummarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.uzummarket.Dto.requestSTO.ProductCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.CategoryResponseDTO;
import uz.pdp.uzummarket.Dto.responceDTO.ProductResponseDTO;
import uz.pdp.uzummarket.service.productService.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDTO> create
            (
                    @RequestBody ProductCreateDTO dto
            ) {
        return ResponseEntity.ok(productService.save(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<ProductResponseDTO> update
            (
                    @RequestParam UUID productId,
                    @RequestBody ProductCreateDTO dto
            ) {
        return ResponseEntity.ok(productService.update(productId, dto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<ProductResponseDTO>> getAll
            (
                    @RequestParam UUID sellerId,
                    @RequestParam(required = false, defaultValue = "0") int size,
                    @RequestParam(required = false, defaultValue = "0") int page
            ) {
        return ResponseEntity.ok(productService.getAll(sellerId,size, page));
    }
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> delete(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.delete(productId));
    }
    @GetMapping("/getById/{productId}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable UUID productId){
        return ResponseEntity.ok(productService.findById(productId));
    }
    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDTO>> search(@RequestParam String word){
        return ResponseEntity.ok(productService.search(word));
    }
}
