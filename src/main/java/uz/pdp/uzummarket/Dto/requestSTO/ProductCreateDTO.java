package uz.pdp.uzummarket.Dto.requestSTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

//@Getter
//@Setter
@AllArgsConstructor@NoArgsConstructor
public class ProductCreateDTO {
    //    private UUID sellerId;
//    private String name;
//    private String description;
//    private Double price;
//    private Integer count;
//    private UUID categoryId;
    private List<UUID> photos;

