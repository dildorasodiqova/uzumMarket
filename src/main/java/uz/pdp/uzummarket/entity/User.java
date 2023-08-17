package uz.pdp.uzummarket.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.uzummarket.enums.Gender;
import uz.pdp.uzummarket.enums.UserRole;

import java.time.LocalDate;

import static uz.pdp.uzummarket.enums.UserRole.USER;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity(name= "users")
@Table(name = "users")
public class User extends BaseEntity{
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole = USER;

    @Column(nullable = false)
    private LocalDate birthDate;
}
