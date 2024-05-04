package kg.talantova.shoppingcart.service;

import kg.talantova.shoppingcart.entity.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailSenderService {
    private final JavaMailSender mailSender;

    public void sendConfirmationEmail(ConfirmationToken confirmationToken, String confirmationUrl) {
        System.out.println("Перед отправкой письма");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("shopping.cart.sup@gmail.com");
        message.setTo(confirmationToken.getUser().getEmail());
        message.setSubject("Подтверждение аккаунта");
        message.setText("Для завершения регистрации перейдите по следующей ссылке: " + confirmationUrl);
        mailSender.send(message);
        System.out.println("После  отправки письма");
    }
}
