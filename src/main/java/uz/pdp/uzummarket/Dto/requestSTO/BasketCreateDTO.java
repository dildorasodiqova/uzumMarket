package uz.pdp.uzummarket.Dto.requestSTO;

import lombok.*;

import java.util.List;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BasketCreateDTO {
    private UUID userId;
    private List<ProductCreateDTO> products;
}
