package uz.pdp.uzummarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "product_photos")
@Table(name = "product_photos")
public class ProductPhotos  extends BaseEntity{
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private Product product;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Attachment photo;

    private int orderIndex;


}
