package uz.pdp.uzummarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name= "product")
public class Product extends BaseEntity{
    @Column(unique = true)
    private String name;
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer count;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Category category;
}
