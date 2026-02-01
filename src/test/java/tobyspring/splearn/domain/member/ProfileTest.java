package tobyspring.splearn.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    @Test
    void profile() {
        new Profile("testyrofile");
        new Profile("123457");
        new Profile("toby100");
    }

    @Test
    void invalidProfile() {
        assertThatThrownBy(
            () -> new Profile("")
        ).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
            () -> new Profile("1234512345123451")
        ).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
            () -> new Profile("A")
        ).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
            () -> new Profile("한글")
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url() {
        Profile profile = new Profile("testyrofile");

        assertThat(profile.url()).isEqualTo("@testyrofile");
    }
}