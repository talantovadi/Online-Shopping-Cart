package kg.talantova.shoppingcart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.talantova.shoppingcart.DTO.token.RefreshTokenRequestDTO;
import kg.talantova.shoppingcart.DTO.user.UserCreateDTO;
import kg.talantova.shoppingcart.DTO.user.UserResponseDTO;
import kg.talantova.shoppingcart.DTO.user.UserSignInRequest;
import kg.talantova.shoppingcart.DTO.user.UserSignInResponse;
import kg.talantova.shoppingcart.service.AuthService;
import kg.talantova.shoppingcart.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "Контроллер для аутентификации"
)
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    @Operation(
            summary = "Зарегистрировать новый аккаунт"
    )
    public ResponseEntity<UserResponseDTO> signUp(@Valid @RequestBody UserCreateDTO userRequest) {
        return authService.createUser(userRequest);
    }



    @PostMapping("/sign-in")
    @Operation(
            summary = "Вход в аккаунт"
    )
    public ResponseEntity<UserSignInResponse> signIn(@Valid @RequestBody UserSignInRequest userRequest) {
        return authService.authenticate(userRequest);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<UserSignInResponse> refreshTokens(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return authService.refreshToken(refreshTokenRequestDTO);
    }
}
