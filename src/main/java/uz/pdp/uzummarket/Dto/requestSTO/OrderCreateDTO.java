package uz.pdp.uzummarket.Dto.requestSTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderCreateDTO {
    private UUID userId;
    private List<OrderProductCreateDTO> products;
}
