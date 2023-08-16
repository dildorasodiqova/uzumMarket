package uz.pdp.uzummarket.Dto.requestSTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import uz.pdp.uzummarket.entity.Attachment;
import uz.pdp.uzummarket.entity.Category;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryCreateDTO {
    private String name;
    private UUID photoId;
    private UUID parentId;
    private boolean active;
}
