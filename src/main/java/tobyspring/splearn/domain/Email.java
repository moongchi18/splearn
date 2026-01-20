package tobyspring.splearn.domain;

import java.util.regex.Pattern;

public record Email(String address) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$");
    public Email{
        if(!EMAIL_PATTERN.matcher(address).matches())
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
    }
}
