package tobyspring.splearn.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;
import tobyspring.splearn.domain.AbstractEntity;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

/**
 * entity 정보는 orm.xml 확인
 */
@Entity
@Getter // getter 자동 생성
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetail extends AbstractEntity {

//    @Embedded
    private Profile profile;

//    @Column(columnDefinition = "TEXT")
    private String introduction;

//    @Column(nullable = false)
    private LocalDateTime registeredAt;

    private LocalDateTime activatedAt;

    private LocalDateTime deactivatedAt;

    static MemberDetail create(){
        MemberDetail detail = new MemberDetail();
        detail.registeredAt = LocalDateTime.now();
        return detail;
    }

    void activate() {
        Assert.isTrue(this.activatedAt == null, "already activated");
        this.activatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        Assert.isTrue(this.deactivatedAt == null, "already deactivated");
        this.deactivatedAt = LocalDateTime.now();
    }

    public void updateInfo(MemberUpdateRequest updateRequest) {
        this.profile = new Profile(updateRequest.profileAddress());
        this.introduction = Objects.requireNonNull(updateRequest.introduction());
    }
}
