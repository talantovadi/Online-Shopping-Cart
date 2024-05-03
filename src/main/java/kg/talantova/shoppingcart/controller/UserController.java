package kg.talantova.shoppingcart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.talantova.shoppingcart.DTO.user.UserResponseDTO;
import kg.talantova.shoppingcart.DTO.user.UserUpdateDTO;
import kg.talantova.shoppingcart.entity.User;
import kg.talantova.shoppingcart.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(
            summary = "Получение всех пользователей магазина"
    )
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(@PageableDefault(page = 0, size = 10, sort = "firstName", direction = Sort.Direction.DESC) Pageable pageable) {
        return userService.getAll(pageable);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение  пользователя магазина по его id"
    )
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable("id") Long id,
                                                   @AuthenticationPrincipal User user) {

        return userService.getUser(id, user);
    }


    @PutMapping("/{id}")
    @Operation(
            summary = "Изменить существующего пользователя"
    )
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable("id") Long userId,
                                                      @Valid @RequestBody UserUpdateDTO updatedUser,
                                                      @AuthenticationPrincipal User user) {
        return userService.updateUser(updatedUser, userId, user);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить  пользователя по его id"
    )
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long userId,
                                           @AuthenticationPrincipal User user) {

        return userService.deleteUser(userId, user);
    }

}
