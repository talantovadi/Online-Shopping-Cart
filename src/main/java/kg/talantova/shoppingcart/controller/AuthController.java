package kg.talantova.shoppingcart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.talantova.shoppingcart.DTO.token.RefreshTokenRequestDTO;
import kg.talantova.shoppingcart.DTO.user.UserCreateDTO;
import kg.talantova.shoppingcart.DTO.user.UserResponseDTO;
import kg.talantova.shoppingcart.DTO.user.UserSignInRequest;
import kg.talantova.shoppingcart.DTO.user.UserSignInResponse;
import kg.talantova.shoppingcart.service.AuthService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<UserResponseDTO> signUp(@Valid @RequestBody UserCreateDTO userRequest,
                                                  HttpServletRequest servletRequest) {
        return authService.createUser(userRequest, servletRequest);
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

    @GetMapping("/verify")
    @Operation(
            summary = "Проверка пользователя через почту"
    )
    public ResponseEntity<String> verificationByEmail(@RequestParam("token") String token){
        return authService.checkUserVerify(token);
    }
}
