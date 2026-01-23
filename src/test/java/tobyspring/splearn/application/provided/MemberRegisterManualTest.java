//package tobyspring.splearn.application.provided;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.test.util.ReflectionTestUtils;
//import tobyspring.splearn.application.MemberService;
//import tobyspring.splearn.application.required.EmailSender;
//import tobyspring.splearn.application.required.MemberRepository;
//import tobyspring.splearn.domain.MemberFixture;
//import tobyspring.splearn.domain.member.Email;
//import tobyspring.splearn.domain.member.Member;
//import tobyspring.splearn.domain.member.MemberStatus;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//import static tobyspring.splearn.domain.MemberFixture.createMemberRegisterRequest;
//
//class MemberRegisterManualTest {
//
//    @Test
//    void registerTestStub() {
//        MemberRegister register = new MemberService(
//                new MemberRepositoryStub(),
//                MemberFixture.createPasswordEncoder(),
//                new EmailSenderStub()
//        );
//
//        Member member = register.register(createMemberRegisterRequest());
//
//        assertThat(member.getId()).isNotNull();
//        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
//
//    }
//
//    @Test
//    void registerTestMock() {
//        EmailSenderMock emailSender = new EmailSenderMock();
//        MemberRegister register = new MemberService(
//                new MemberRepositoryStub(),
//                MemberFixture.createPasswordEncoder(),
//                emailSender
//        );
//
//        Member member = register.register(createMemberRegisterRequest());
//
//        assertThat(member.getId()).isNotNull();
//        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
//
//        assertThat(emailSender.emails).hasSize(1);
//        assertThat(emailSender.emails.getFirst()).isEqualTo(member.getEmail());
//    }
//
//    @Test
//    void registerTestMockito() {
//        EmailSender emailSender = mock(EmailSender.class);
//        MemberRegister register = new MemberService(
//                new MemberRepositoryStub(),
//                MemberFixture.createPasswordEncoder(),
//                emailSender
//        );
//
//        Member member = register.register(createMemberRegisterRequest());
//
//        assertThat(member.getId()).isNotNull();
//        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
//
//        verify(emailSender).send(eq(member.getEmail()), any(), any());
//
//    }
//
//    static class MemberRepositoryStub implements MemberRepository {
//        @Override
//        public Member save(Member member) {
//            ReflectionTestUtils.setField(member, "id", 1L);
//            return member;
//        }
//
//        @Override
//        public Optional<Member> findByEmail(Email email) {
//            return Optional.empty();
//        }
//
//        @Override
//        public Optional<Member> findById(Long memberId) {
//            return Optional.empty();
//        }
//    }
//
//    static class EmailSenderStub implements EmailSender {
//        @Override
//        public void send(Email email, String subject, String body) {
//
//        }
//    }
//
//    static class EmailSenderMock implements EmailSender {
//        public List<Email> emails = new ArrayList<>();
//
//        @Override
//        public void send(Email email, String subject, String body) {
//            emails.add(email);
//        }
//    }
//}