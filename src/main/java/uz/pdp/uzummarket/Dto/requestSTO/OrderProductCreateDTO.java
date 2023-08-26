package uz.pdp.uzummarket.Dto.requestSTO;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.uzummarket.entity.Order;
import uz.pdp.uzummarket.entity.Product;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderProductCreateDTO {
    private UUID productId;
    private int count;
    private Double price;
}




