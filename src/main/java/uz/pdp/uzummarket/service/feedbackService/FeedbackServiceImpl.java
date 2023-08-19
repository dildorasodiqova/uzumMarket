package uz.pdp.uzummarket.service.feedbackService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.Dto.requestSTO.FeedBackCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.FeedbackResponseDTO;
import uz.pdp.uzummarket.entity.Feedback;
import uz.pdp.uzummarket.entity.Product;
import uz.pdp.uzummarket.entity.User;
import uz.pdp.uzummarket.exception.DataNotFoundException;
import uz.pdp.uzummarket.repository.FeedBackRepository;
import uz.pdp.uzummarket.repository.ProductRepository;
import uz.pdp.uzummarket.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl  implements FeedbackService{
    private final FeedBackRepository feedBackRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public FeedbackResponseDTO create(FeedBackCreateDTO dto) {
        Optional<Product> product = productRepository.findById(dto.getProductId());
        Optional<User> user = userRepository.findById(dto.getUserId());

        Feedback feedback = new Feedback(product.get(),user.get(), dto.getRate(), dto.getText());
        Feedback save = feedBackRepository.save(feedback);
        return parse(save);
    }
    private FeedbackResponseDTO parse(Feedback dto){
        FeedbackResponseDTO feedbackResponseDTO = new FeedbackResponseDTO(dto.getProduct().getId(),dto.getUser().getFirstName(), dto.getRate(),dto.getText());
        return feedbackResponseDTO;
    }

    @Override
    public FeedbackResponseDTO findById(UUID feedbackId) {
        Feedback feedback = feedBackRepository.findById(feedbackId).orElseThrow(() -> new DataNotFoundException("Feedback not found"));
        FeedbackResponseDTO parse = parse(feedback);
        return parse;
    }

    @Override
    public List<FeedbackResponseDTO> getByProductId(UUID productId) {
        List<Feedback> allByProductId = feedBackRepository.findAllByProductId(productId);
        List<FeedbackResponseDTO> list = new ArrayList<>();
        for (Feedback feedback : allByProductId) {
            FeedbackResponseDTO parse = parse(feedback);
            list.add(parse);
        }
        return list;
    }

    @Override
    public void delete(UUID feedbackId) {
        Feedback feedback = feedBackRepository.findById(feedbackId).orElseThrow(() -> new DataNotFoundException("Feedback not found !"));
        feedBackRepository.delete(feedback);
    }
}
