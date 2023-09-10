package uz.pdp.uzummarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.uzummarket.Dto.requestSTO.FeedBackCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
import uz.pdp.uzummarket.Dto.responceDTO.CategoryResponseDTO;
import uz.pdp.uzummarket.Dto.responceDTO.FeedbackResponseDTO;
import uz.pdp.uzummarket.service.feedbackService.FeedbackService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<FeedbackResponseDTO>> create(@RequestBody FeedBackCreateDTO createDTO) {
        return ResponseEntity.ok(feedbackService.create(createDTO));
    }

    @GetMapping("getById/{feedbackId}")
    public ResponseEntity<BaseResponse<FeedbackResponseDTO>> getById(@PathVariable UUID feedbackId){
        return ResponseEntity.ok(feedbackService.findById(feedbackId));
    }

    @DeleteMapping("/delete/{feedbackId}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable UUID feedbackId) {
        return ResponseEntity.ok(feedbackService.delete(feedbackId));
    }

    @GetMapping("feedbackOfProduct{productId}")
    public ResponseEntity<BaseResponse<List<FeedbackResponseDTO>>> feedbackOfProduct(@PathVariable UUID productId){
        return ResponseEntity.ok(feedbackService.getByProductId(productId));
    }




}
