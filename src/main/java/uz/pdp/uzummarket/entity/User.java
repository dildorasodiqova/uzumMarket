package uz.pdp.uzummarket.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.uzummarket.enums.Gender;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name= "user")
public class User extends BaseEntity{
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private Gender gender;
    private LocalDate birthDate;
}
