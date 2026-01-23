package tobyspring.splearn.adapter.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;
import tobyspring.splearn.application.required.EmailSender;
import tobyspring.splearn.domain.member.Email;

@Component
@Fallback // EmailSender 구현체가 없을 때 이 구현체가 사용된다.
public class DummyEmailSender implements EmailSender {
    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("DummyEmailSender SEND_EMAIL to: " + email);
    }
}
