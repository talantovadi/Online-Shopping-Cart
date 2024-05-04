package kg.talantova.shoppingcart.service;

import jakarta.servlet.http.HttpServletRequest;
import kg.talantova.shoppingcart.DTO.token.RefreshTokenRequestDTO;
import kg.talantova.shoppingcart.DTO.user.UserCreateDTO;
import kg.talantova.shoppingcart.DTO.user.UserResponseDTO;
import kg.talantova.shoppingcart.DTO.user.UserSignInRequest;
import kg.talantova.shoppingcart.DTO.user.UserSignInResponse;
import kg.talantova.shoppingcart.entity.ConfirmationToken;
import kg.talantova.shoppingcart.entity.RefreshToken;
import kg.talantova.shoppingcart.entity.User;
import kg.talantova.shoppingcart.enums.Roles;
import kg.talantova.shoppingcart.enums.UserStatus;
import kg.talantova.shoppingcart.exception.NotFoundException;
import kg.talantova.shoppingcart.exception.NotValidException;
import kg.talantova.shoppingcart.mapper.UserMapper;
import kg.talantova.shoppingcart.repository.ConfirmationTokenRepository;
import kg.talantova.shoppingcart.repository.UserRepository;
import kg.talantova.shoppingcart.security.jwt.JwtService;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper mapper;
    private final RefreshTokenService refreshTokenService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final MailSenderService mailService;

    public ResponseEntity<UserSignInResponse> authenticate(UserSignInRequest userRequest) {
        User userEntity = userRepository.findByEmail(userRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("Пользователь с такой почтой не найден"));

        if (!passwordEncoder.matches(userRequest.getPassword(), userEntity.getPassword())) {
            throw new NotValidException("Вы ввели неверный пароль");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getEmail(),
                        userRequest.getPassword()
                )
        );

        String jwtToken = jwtService.generateToken(userEntity);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userRequest.getEmail());
        UserSignInResponse response = new UserSignInResponse(jwtToken, refreshToken.getToken());

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<UserResponseDTO> createUser(UserCreateDTO user, HttpServletRequest servletRequest) {
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            throw new NotValidException("Your password and confirm password not equals");
        }
        System.out.println("Пароли совпадают");
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new NotValidException("User with that email address is already exist");
        }
        System.out.println("Почта уникальна");
        User userEntity = mapper.toEntity(user);
        userEntity.setUserCart(Collections.emptyList());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(Roles.ROLE_USER);
        userEntity.setUserStatus(UserStatus.NOT_VERIFIED);
        userRepository.save(userEntity);

        sendConfirmationToken(userEntity, servletRequest);
        return new ResponseEntity<>(mapper.toUserResponse(userEntity), HttpStatus.OK);
    }

    public ResponseEntity<UserSignInResponse> refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user);
                    return new ResponseEntity(new UserSignInResponse(accessToken, refreshTokenRequestDTO.getToken()), HttpStatus.OK);
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
    }


    public void sendConfirmationToken(User user, HttpServletRequest request) {
        ConfirmationToken token = createConfirmationToken(user);
        String confirmationUrl = getConfirmationUrl(request, token.getToken());
        System.out.println("Сcылка на подтверждение " + confirmationUrl);
        mailService.sendConfirmationEmail(token, confirmationUrl);
    }

    private ConfirmationToken createConfirmationToken(User user) {
        String randomString = UUID.randomUUID().toString();

        Optional<ConfirmationToken> token = confirmationTokenRepository.findByUser(user);
        if (token.isPresent()) {
            token.get().setToken(randomString);
            token.get().setDates(LocalDateTime.now());
            return confirmationTokenRepository.save(token.get());
        } else {
            ConfirmationToken newToken = new ConfirmationToken(randomString, user);
            return confirmationTokenRepository.save(newToken);
        }
    }

    private String getConfirmationUrl(HttpServletRequest servletRequest, String token) {
        return "http://" + servletRequest.getServerName() + ":"
                + servletRequest.getServerPort() + "/api/auth/verify?token=" + token;
    }

    private boolean isTokenExpired(ConfirmationToken token) {
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.isAfter(token.getExpiredAt());
    }

    public ResponseEntity<String> checkUserVerify(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Такого токена не существует"));

        if (isTokenExpired(confirmationToken)) {
            throw new NotValidException("Ссылка для подтверждения аккаунта истекла");
        }
        User userEntity = confirmationToken.getUser();
        userEntity.setUserStatus(UserStatus.VERIFIED);
        userRepository.save(userEntity);
        return ResponseEntity.ok("<h1>Вы подтвердили свой аккаунт, перейдите на страницу входа.<h1>");
    }

}

