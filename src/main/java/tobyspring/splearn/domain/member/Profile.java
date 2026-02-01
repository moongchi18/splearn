package tobyspring.splearn.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

//@Embeddable
public record Profile(
//        @Column(length = 20)
        String address) {
    private static final Pattern PROFILE_ADDRESS_PATTERN =
            Pattern.compile("[a-z0-9]+");
    public Profile{
        if(!PROFILE_ADDRESS_PATTERN.matcher(address).matches())
            throw new IllegalArgumentException("올바른 닉네임 형식이 아닙니다.");

        if(address.length() > 15) throw new IllegalArgumentException("프로필 주소는 최대 150자입니다.");
    }

    public String url(){
        return "@" + address;
    }
}
