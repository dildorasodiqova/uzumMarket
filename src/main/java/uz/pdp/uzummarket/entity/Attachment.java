package uz.pdp.uzummarket.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "imageDate")
@Builder
public class Attachment extends BaseEntity{
    private String name;
    private String contentType;
    private long size;
    @Lob
    @Column(name = "bytes")
    private byte[] bytes;


}
