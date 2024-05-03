package kg.talantova.shoppingcart.DTO.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserUpdateDTO {
    @NotBlank(message = "Имя - обязательное поле ")
    @Size(min = 3, max = 50, message = "First name should be between 3 and 50 characters")
    private String firstName;
    @NotBlank(message = "Фамилия - обязательное поле ")
    @Size(min = 3, max = 50, message = "Last name should be between 3 and 50 characters")
    private String lastName;
    @NotBlank(message = "Укажите номер телефона")
    @Size(min = 10, max = 30, message = "Phone should be between 10 and 30characters")
    @Pattern(regexp = "^\\+?[0-9]+$", message = "Phone number should have only numbers")
    private String phone;

    @NotBlank(message = "Укажите ваш email адрес")
    @Size(max = 150, message = "Email should be less than 150 characters")
    @Email(message = "Enter valid email address")
    private String email;

}