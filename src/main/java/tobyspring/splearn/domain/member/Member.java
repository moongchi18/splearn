package tobyspring.splearn.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.util.Assert;
import tobyspring.splearn.domain.AbstractEntity;
import tobyspring.splearn.domain.shared.Email;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

/**
 * entity 정보는 orm.xml 확인
 */
@Entity
//@Table(name = "member", uniqueConstraints =
//    @UniqueConstraint(name = "uk_member_email_address", columnNames = "email_address")
//)
@Getter // getter 자동 생성
@ToString(callSuper = true, exclude = "detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NaturalIdCache // 영속성 컨텍스트의 캐쉬값 조회
public class Member extends AbstractEntity {

//    @Embedded
    @NaturalId // hibernate 중복방지 어노테이션
    private Email email;

//    @Column(length = 100, nullable = false)
    private String nickname;

//    @Column(length = 200, nullable = false)
    private String passwordHash;

//    @Column(length = 50, nullable = false)
//    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    private MemberDetail detail;

//  factory
    public static Member register(MemberRegisterRequest createRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();

        member.email = new Email(createRequest.email());
        member.nickname = requireNonNull(createRequest.nickname());
        member.passwordHash = passwordEncoder.encode(requireNonNull(createRequest.password()));

        member.status = MemberStatus.PENDING;
        member.detail = MemberDetail.create();

        return member;
    }

    // Business Logic
    public void activate() {
//        if(status != MemberStatus.PENDING) { throw new IllegalStateException("PENDING 상태가 아닙니다."); }
        state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

        this.status = MemberStatus.ACTIVE;
        this.detail.activate();
    }

    public void deactivate() {
        state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

        this.status = MemberStatus.DEACTIVATED;
        this.detail.deactivate();
    }

    public boolean isActive() {
        return this.status == MemberStatus.ACTIVE;
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void changeNickname(String nickname) {
        this.nickname = requireNonNull(nickname);
    }

    public void updateInfo(MemberUpdateRequest updateRequest) {
        Assert.state(this.status == MemberStatus.ACTIVE, "Active 상태에서만 업데이트할 수 있습니다.");

        this.nickname = Objects.requireNonNull(updateRequest.nickname());
        this.detail.updateInfo(updateRequest);

    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }

    public void updatePassword(String newPassword, PasswordEncoder passwordEncoder) {
        requireNonNull(newPassword);
        this.passwordHash = passwordEncoder.encode(newPassword);
    }
}
