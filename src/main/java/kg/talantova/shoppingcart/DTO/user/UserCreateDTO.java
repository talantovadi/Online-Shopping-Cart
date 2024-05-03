package kg.talantova.shoppingcart.DTO.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class UserCreateDTO {
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

    @NotBlank(message = "Укажите ваш пароль")
    @Size(max = 150, message = "Password should be less than 150 characters")
    private String password;
    @NotBlank(message = "Введите пароль повторно")
    @Size(max = 150, message = "ConfirmPassword should be less than 150 characters")
    private String confirmPassword;

    public UserCreateDTO(String firstName, String lastName, String phone,  String email, String password, String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }



    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
