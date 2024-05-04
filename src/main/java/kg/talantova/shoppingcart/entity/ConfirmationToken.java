package kg.talantova.shoppingcart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken {
    private static final int EXPIRATION_TIME_IN_HOURS = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public ConfirmationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        expiredAt = createdAt.plus(EXPIRATION_TIME_IN_HOURS, ChronoUnit.HOURS);
    }

    public void setDates(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        expiredAt = createdAt.plus(EXPIRATION_TIME_IN_HOURS, ChronoUnit.HOURS);
    }


}