package tobyspring.splearn.domain;

import org.junit.jupiter.api.Test;
import tobyspring.splearn.domain.shared.Email;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {

    @Test
    void equality() {
        Email email1 = new Email("myEmail@splearn.com");
        Email email2 = new Email("myEmail@splearn.com");

        assertThat(email1).isEqualTo(email2);
    }
}