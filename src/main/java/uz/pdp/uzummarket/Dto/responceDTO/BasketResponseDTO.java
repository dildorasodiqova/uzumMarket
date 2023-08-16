package uz.pdp.uzummarket.Dto.responceDTO;

import lombok.*;
import uz.pdp.uzummarket.Dto.requestSTO.ProductCreateDTO;

import java.util.List;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BasketResponseDTO {
    private UUID id;
    private UUID userId;
    private List<ProductCreateDTO> products;
}
