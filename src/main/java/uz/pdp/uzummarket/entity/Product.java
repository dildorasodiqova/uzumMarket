package uz.pdp.uzummarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name= "product")
public class Product extends BaseEntity{
    @Column(unique = true)
    private String name;
    private String description;
    private Double price;
    private Integer count;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products")
    @JsonIgnore
    private List<Bucket> buckets;
}
