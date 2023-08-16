package uz.pdp.uzummarket.Dto.requestSTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {
    private String name;
    private String phoneNumber;
    private String password;
}
