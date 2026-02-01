package tobyspring.splearn.application.provided;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import tobyspring.splearn.SplearnTestConfiguration;
import tobyspring.splearn.application.member.provided.MemberFinder;
import tobyspring.splearn.application.member.provided.MemberRegister;
import tobyspring.splearn.domain.member.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberFinderTest(MemberFinder memberFinder, MemberRegister memberRegister, EntityManager entityManager) {

    @Test
    void find() {
        Member member = memberRegister.register(tobyspring.splearn.domain.MemberFixture.createMemberRegisterRequest());

        entityManager.flush();
        entityManager.clear();

        Member foundMember = memberFinder.find(member.getId());

        assertThat(member.getId()).isEqualTo(foundMember.getId());
    }

    @Test
    void findFail() {
        assertThatThrownBy(
            () -> memberFinder.find(999L)
        ).isInstanceOf(IllegalArgumentException.class);

    }
}