package uz.pdp.uzummarket.Dto.responceDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BasketResponseDTO {
    private UUID userId;
    private UUID basketId;
    private List<BasketProductDTO> products;
    private double totalPrice;
}
