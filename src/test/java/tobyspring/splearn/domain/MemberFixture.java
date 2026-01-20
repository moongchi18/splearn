package tobyspring.splearn.domain;

import org.junit.jupiter.api.BeforeEach;

public class MemberFixture {
    public static MemberRegisterRequest createMemberRegisterRequest() {
        return createMemberRegisterRequest("myEmail@splearn.app");
    }

    public static MemberRegisterRequest createMemberRegisterRequest(String email) {
        return new MemberRegisterRequest(email, "moongchi", "mySecret");
    }

    public static PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }
}
