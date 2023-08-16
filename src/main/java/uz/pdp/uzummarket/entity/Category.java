package uz.pdp.uzummarket.entity;

import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Default;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name= "category")
@Builder(setterPrefix = "set")
@Table(name = "category")
public class Category extends BaseEntity{

    @Column(unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Attachment photo;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private Category parent;

    private boolean active = true;
}
