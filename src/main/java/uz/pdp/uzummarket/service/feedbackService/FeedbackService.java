package uz.pdp.uzummarket.service.feedbackService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import uz.pdp.uzummarket.Dto.requestSTO.FeedBackCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.FeedbackResponseDTO;

import java.util.List;
import java.util.UUID;

public interface FeedbackService {
    FeedbackResponseDTO create(FeedBackCreateDTO dto);
    FeedbackResponseDTO findById(UUID feedbackId);
    List<FeedbackResponseDTO> getByProductId(UUID productId);
    void delete(UUID feedbackId);
}
