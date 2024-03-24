package kg.talantova.shoppingcart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.talantova.shoppingcart.DTO.UserCreateDTO;
import kg.talantova.shoppingcart.DTO.UserResponseDTO;
import kg.talantova.shoppingcart.DTO.UserUpdateDTO;
import kg.talantova.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(
        name = "Контроллер для управлением user"
)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(
            summary = "Создать нового пользователя"
    )
    public ResponseEntity<UserResponseDTO> signUp(@Valid @RequestBody UserCreateDTO userRequest) {
        return userService.createUser(userRequest);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Изменить существующего пользователя"
    )
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable("id") Long userId,
                                                      @Valid @RequestBody UserUpdateDTO updatedUser) {
        return userService.updateUser(updatedUser, userId);
    }

}
