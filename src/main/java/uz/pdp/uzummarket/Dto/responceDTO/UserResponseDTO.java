package uz.pdp.uzummarket.Dto.responceDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.pdp.uzummarket.enums.Gender;
import uz.pdp.uzummarket.enums.UserRole;

import java.time.LocalDate;
import java.util.UUID;

import static uz.pdp.uzummarket.enums.UserRole.USER;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private UUID id;
    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String password;

    private String gender;

    private String userRole;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate birthDate;

}
