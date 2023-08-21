package uz.pdp.uzummarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.uzummarket.Dto.requestSTO.BasketAddDTO;
import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
import uz.pdp.uzummarket.Dto.responceDTO.BasketResponseDTO;
import uz.pdp.uzummarket.service.bucketService.BasketService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basketController")
public class BasketController {
    private final BasketService basketService;
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<BasketResponseDTO>> add(@RequestBody BasketAddDTO dto){
        return ResponseEntity.ok(basketService.create(dto.getUserId(), dto.getProductId(), dto.getCount()));
    }
}
