package uz.pdp.uzummarket.service.feedbackService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import uz.pdp.uzummarket.Dto.requestSTO.FeedBackCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
import uz.pdp.uzummarket.Dto.responceDTO.FeedbackResponseDTO;

import java.util.List;
import java.util.UUID;

public interface FeedbackService {
    BaseResponse<FeedbackResponseDTO> create(FeedBackCreateDTO dto);
    BaseResponse<FeedbackResponseDTO> findById(UUID feedbackId);
    BaseResponse<List<FeedbackResponseDTO>> getByProductId(UUID productId);
    BaseResponse<String> delete(UUID feedbackId);
}
