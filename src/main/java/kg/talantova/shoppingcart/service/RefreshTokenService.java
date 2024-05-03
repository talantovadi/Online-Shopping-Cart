package kg.talantova.shoppingcart.service;

import kg.talantova.shoppingcart.entity.RefreshToken;
import kg.talantova.shoppingcart.exception.NotFoundException;
import kg.talantova.shoppingcart.repository.RefreshTokenRepository;
import kg.talantova.shoppingcart.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    public RefreshToken createRefreshToken(String username){
        RefreshToken refreshToken = RefreshToken
                .builder()
                .user(userRepository.findByEmail(username)
                        .orElseThrow(() -> new NotFoundException("user with username " + username + " not found")))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(300000)) //5 minutes
                .build();
        return refreshTokenRepository.save(refreshToken);
    }



    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }


}
