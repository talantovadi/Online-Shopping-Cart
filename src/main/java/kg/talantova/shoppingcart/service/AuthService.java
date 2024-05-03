package kg.talantova.shoppingcart.service;

import kg.talantova.shoppingcart.DTO.token.RefreshTokenRequestDTO;
import kg.talantova.shoppingcart.DTO.user.UserCreateDTO;
import kg.talantova.shoppingcart.DTO.user.UserResponseDTO;
import kg.talantova.shoppingcart.DTO.user.UserSignInRequest;
import kg.talantova.shoppingcart.DTO.user.UserSignInResponse;
import kg.talantova.shoppingcart.entity.RefreshToken;
import kg.talantova.shoppingcart.entity.User;
import kg.talantova.shoppingcart.enums.Roles;
import kg.talantova.shoppingcart.exception.NotFoundException;
import kg.talantova.shoppingcart.exception.NotValidException;
import kg.talantova.shoppingcart.mapper.UserMapper;
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

import java.util.Collections;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper mapper;
    private final RefreshTokenService refreshTokenService;

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

    public ResponseEntity<UserResponseDTO> createUser(UserCreateDTO user) {
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            throw new NotValidException("Your password and confirm password not equals");
        }
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new NotValidException("User with that email address is already exist");
        }
        User userEntity = mapper.toEntity(user);
        userEntity.setUserCart(Collections.emptyList());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(Roles.ROLE_USER);
        userRepository.save(userEntity);
        return new ResponseEntity<>(mapper.toUserResponse(userEntity), HttpStatus.OK);
    }

    public ResponseEntity<UserSignInResponse> refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user);
                    return new ResponseEntity(new UserSignInResponse(accessToken, refreshTokenRequestDTO.getToken()), HttpStatus.OK);
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }
}

