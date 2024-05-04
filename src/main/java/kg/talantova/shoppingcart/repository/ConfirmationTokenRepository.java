package kg.talantova.shoppingcart.repository;

import kg.talantova.shoppingcart.entity.ConfirmationToken;
import kg.talantova.shoppingcart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);
    Optional<ConfirmationToken> findByUser(User user);
}
