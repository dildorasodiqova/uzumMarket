package uz.pdp.uzummarket.Dto.requestSTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductRequestDto {
    private String name;
    private String description;
    private Double price;
    private Integer count;
    private UUID categoryId;
}
