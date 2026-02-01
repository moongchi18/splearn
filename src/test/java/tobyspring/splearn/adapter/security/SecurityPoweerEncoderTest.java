package tobyspring.splearn.adapter.security;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
class SecurityPoweerEncoderTest {
    SecurityPoweerEncoder securityPoweerEncoder = new SecurityPoweerEncoder();
    @Test
    void encode() {
        String passwordHash = securityPoweerEncoder.encode("password");

        assertThat(securityPoweerEncoder.matches("password", passwordHash)).isTrue();
        assertThat(securityPoweerEncoder.matches("WRONG", passwordHash)).isFalse();
    }
}