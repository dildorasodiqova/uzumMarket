package uz.pdp.uzummarket.Dto.requestSTO;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.uzummarket.entity.Product;
import uz.pdp.uzummarket.entity.User;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FeedBackCreateDTO {
    private UUID productId;
    private UUID userId;
    private int rate;
    private String text;
}
