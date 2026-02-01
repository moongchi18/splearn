package tobyspring.splearn.application.provided;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import tobyspring.splearn.SplearnTestConfiguration;
import tobyspring.splearn.application.member.provided.MemberRegister;
import tobyspring.splearn.domain.member.*;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tobyspring.splearn.domain.MemberFixture.createMemberRegisterRequest;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {

    @Test
    void register() {
        Member member = memberRegister.register(createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        MemberRegisterRequest memberRegisterRequest = createMemberRegisterRequest();
        memberRegister.register(memberRegisterRequest);

        assertThatThrownBy(
            () -> memberRegister.register(memberRegisterRequest)
        ).isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void activate() {
        Member member = memberRegister.register(createMemberRegisterRequest());
//        entityManager.flush();
//        entityManager.clear();

        Member activatedMember = memberRegister.activate(member.getId());

//        entityManager.flush();

        assertThat(activatedMember.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void deactivate() {
        Member member = registerMember();
        // memberRegister.activate()를 사용하여 DB에 ACTIVE 상태를 반영
        Member activatedMember = memberRegister.activate(member.getId());
        entityManager.flush();

        Member deactivatedMember = memberRegister.deactivate(activatedMember.getId());

        entityManager.flush();
        entityManager.clear();

        assertThat(deactivatedMember.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(deactivatedMember.getDetail().getDeactivatedAt()).isNotNull();
    }

    private Member registerMember() {
        Member member = memberRegister.register(createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();
        return member;
    }
    private Member registerMember(String email) {
        Member member = memberRegister.register(createMemberRegisterRequest(email));
        entityManager.flush();
        entityManager.clear();
        return member;
    }

    @Test
    void memberRegisterRequestFail() {
        checkValidation(new MemberRegisterRequest("toby@splearn.app", "Toby", "longsecret"));
        checkValidation(new MemberRegisterRequest("toby@splearn.app", "Charlie_____________________________", "longsecret"));
        checkValidation(new MemberRegisterRequest("tobysplearn.app", "Charlie", "longsecret"));
    }

    private void checkValidation(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void updateInfo() {
        Member member = registerMember();

        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        Member updated = memberRegister.updateInfo(member.getId(), new MemberUpdateRequest("Peter", "toby100", "자기소개"));
        entityManager.flush();

        assertThat(updated.getDetail().getProfile().address()).isEqualTo("toby100");
    }

    @Test
    void updateInfoFail() {
        Member member = registerMember();
        memberRegister.activate(member.getId());
        Member updated = memberRegister.updateInfo(member.getId(), new MemberUpdateRequest("Peter", "toby100", "자기소개"));

        Member member2 = registerMember("myEmail@splearn.app");
        entityManager.flush();
        entityManager.clear();

        assertThat(updated.getDetail().getProfile().address()).isEqualTo("toby100");
    }

}
