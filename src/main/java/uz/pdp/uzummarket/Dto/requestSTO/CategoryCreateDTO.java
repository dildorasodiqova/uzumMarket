package uz.pdp.uzummarket.Dto.requestSTO;
import lombok.*;
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

