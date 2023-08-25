package uz.pdp.uzummarket.Dto.responceDTO;

import lombok.*;
import uz.pdp.uzummarket.entity.Attachment;

import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryResponseDTO {
    private UUID id;
    private String name;
    private boolean active;
    private UUID photoId;
    private UUID parentId;
}
