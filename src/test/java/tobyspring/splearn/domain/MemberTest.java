package tobyspring.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberStatus;
import tobyspring.splearn.domain.member.MemberUpdateRequest;
import tobyspring.splearn.domain.member.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tobyspring.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static tobyspring.splearn.domain.MemberFixture.createPasswordEncoder;

class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        passwordEncoder = createPasswordEncoder();
        member = Member.register(createMemberRegisterRequest(), passwordEncoder);
    }

    @Test
    void createMember() {
        assertThat(member.getStatus())
                .isNotNull()
                .isEqualTo(MemberStatus.PENDING);
        assertThat(member.getDetail().getRegisteredAt()).isNotNull();
    }

    @Test
    void activate() {
        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
    }

    @Test
    void activateFail() {
        member.activate();

        assertThatThrownBy(() -> {
            member.activate();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void deactivateFail() {
        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);
    }
    
    @Test
    void varifyPassword() {
        assertThat(member.verifyPassword("very_secret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("ySecret", passwordEncoder)).isFalse();
    }

    @Test
    void changeNickname() {
        assertThat(member.getNickname()).isEqualTo("moongchi");

        member.changeNickname("JJANG");

        assertThat(member.getNickname()).isEqualTo("JJANG");
    }

    @Test
    void changePassword() {
        member.changePassword("verySecret", passwordEncoder);
        assertThat(member.verifyPassword("verySecret", passwordEncoder)).isTrue();
    }

    @Test
    void isActive() {
        assertThat(member.isActive()).isFalse();

        member.activate();

        assertThat(member.isActive()).isTrue();
    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(() -> {
            Member.register(createMemberRegisterRequest("invalid-email"), passwordEncoder);
        }).isInstanceOf(IllegalArgumentException.class);

//        Member.create(new MemberCreateRequest("invalid-email", "moongchi", "mySecret"), passwordEncoder);
    }

    @Test
    void updateInfo() {
        member.activate();

        MemberUpdateRequest updateInfo = new MemberUpdateRequest("Lee", "toby100", "자기소개");
        member.updateInfo(updateInfo);

        assertThat(member.getNickname()).isEqualTo(updateInfo.nickname());
        assertThat(member.getDetail().getProfile().address()).isEqualTo(updateInfo.profileAddress());
        assertThat(member.getDetail().getIntroduction()).isEqualTo(updateInfo.introduction());

    }

    @Test
    void updateInfoFail() {
        assertThatThrownBy(
            () -> {
                MemberUpdateRequest updateInfo = new MemberUpdateRequest("Lee", "toby100", "자기소개");
                member.updateInfo(updateInfo);
            }
        ).isInstanceOf(IllegalStateException.class);

    }
}