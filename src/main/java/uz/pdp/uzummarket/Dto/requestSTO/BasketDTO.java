package uz.pdp.uzummarket.Dto.requestSTO;

import lombok.*;

import java.util.List;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BasketDTO {
    private UUID userId;
    private List<ProductRequestDto> products;
}
