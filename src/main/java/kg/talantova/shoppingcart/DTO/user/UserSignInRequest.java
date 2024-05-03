package kg.talantova.shoppingcart.DTO.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserSignInRequest {
    @NotBlank
    @Size(max = 150, message = "Email should be less than 150 characters")
    @Email(message = "Enter valid email address")
    private  String email;
    @NotBlank
    @Size(max = 150, message = "Password should be less than 150 characters")
    private String password;
}
