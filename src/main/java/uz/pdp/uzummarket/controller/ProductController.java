package uz.pdp.uzummarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.uzummarket.Dto.requestSTO.ProductCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.ProductResponseDTO;
import uz.pdp.uzummarket.service.productService.ProductService;

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
                    @RequestParam(required = false, defaultValue = "10") int size,
                    @RequestParam(required = false, defaultValue = "0") int page
            ) {
        return ResponseEntity.ok(productService.getAll(sellerId,size, page));
    }
}
