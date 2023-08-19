package uz.pdp.uzummarket.Dto.responceDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.uzummarket.entity.OrderProduct;
import uz.pdp.uzummarket.entity.User;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderResponseDTO {
    private UUID userId;
    private double price;
    private List<OrderProductResponseDTO> orderProducts;
}
