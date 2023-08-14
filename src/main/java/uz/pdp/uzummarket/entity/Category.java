package uz.pdp.uzummarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name= "category")
public class Category extends BaseEntity{
    private String name;
    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Attachment photo;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private Category parent;
}
