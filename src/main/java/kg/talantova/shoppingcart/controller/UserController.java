package kg.talantova.shoppingcart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.talantova.shoppingcart.DTO.user.UserCreateDTO;
import kg.talantova.shoppingcart.DTO.user.UserResponseDTO;
import kg.talantova.shoppingcart.DTO.user.UserUpdateDTO;
import kg.talantova.shoppingcart.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    @Operation(
            summary = "Получение всех пользователей магазина"
    )
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(@PageableDefault(page = 0, size = 10, sort = "firstName", direction = Sort.Direction.DESC) Pageable pageable) {
        return userService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение  пользователя магазина по его id"
    )
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
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

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить  пользователя по его id"
    )
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long userId) {
        return userService.deleteUser(userId);
    }

}
