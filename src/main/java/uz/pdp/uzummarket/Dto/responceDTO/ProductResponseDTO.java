package uz.pdp.uzummarket.Dto.responceDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import uz.pdp.uzummarket.entity.Basket;
import uz.pdp.uzummarket.entity.Category;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
    private UUID id;
    private UUID sellerId;
    private String name;
    private String description;
    private Double price;
    private Integer count;
    private UUID categoryId;
    private List<UUID> photos;

}
