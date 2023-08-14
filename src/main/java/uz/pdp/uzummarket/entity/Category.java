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
    @OneToMany(mappedBy = "categories", cascade = CascadeType.ALL)
    private List<Attachment> attachments;
}
