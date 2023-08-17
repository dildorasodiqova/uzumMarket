package uz.pdp.uzummarket.Dto.requestSTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInDTO {
    private String email;
    private String name;
    private String password;
}
