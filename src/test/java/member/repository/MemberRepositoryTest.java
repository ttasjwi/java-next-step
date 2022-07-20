package member.repository;

import member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MemberRepository 테스트")
class MemberRepositoryTest {

    private MemberRepository memberRepository;

    @BeforeEach
    void serUp() {
        memberRepository = new MemberRepository();
    }

    private Member sampleMember() {
        return Member.builder()
                .loginId("testMember")
                .password("1234!").name("test-member")
                .email("testmember@gmail.com")
                .build();
    }

    @Nested
    @DisplayName("Member를 저장 후 findById로 같은 식별자의 회원을 찾았을 때")
    class join_and_findById_Test {

        private Member saveMember = sampleMember();

        @Test
        @DisplayName("같은 식별자를 가지고 있어야한다.")
        public void idEqualsTest() {
            // given
            memberRepository.save(saveMember);

            // when
            Member findMember = memberRepository.findById(saveMember.getId()).get();

            // then
            assertThat(findMember.getId()).isEqualTo(saveMember.getId());
        }

        @Test
        @DisplayName("같은 loginId를 가지고 있어야한다.")
        public void loginIdEqualsTest() {
            // given
            memberRepository.save(saveMember);

            // when
            Member findMember = memberRepository.findById(saveMember.getId()).get();

            // then
            assertThat(findMember.getLoginId()).isEqualTo(saveMember.getLoginId());
        }

        @Test
        @DisplayName("같은 password를 가지고 있어야한다.")
        public void passwordEqualsTest() {
            // given
            memberRepository.save(saveMember);

            // when
            Member findMember = memberRepository.findById(saveMember.getId()).get();

            // then
            assertThat(findMember.getPassword()).isEqualTo(saveMember.getPassword());
        }

        @Test
        @DisplayName("같은 이름을 가지고 있어야한다.")
        public void nameEqualsTest() {
            // given
            memberRepository.save(saveMember);

            // when
            Member findMember = memberRepository.findById(saveMember.getId()).get();

            // then
            assertThat(findMember.getName()).isEqualTo(saveMember.getName());
        }

        @Test
        @DisplayName("같은 이메일을 가지고 있어야한다.")
        public void emailEqualsTest() {
            // given
            memberRepository.save(saveMember);

            // when
            Member findMember = memberRepository.findById(saveMember.getId()).get();

            // then
            assertThat(findMember.getEmail()).isEqualTo(saveMember.getEmail());
        }
    }

    @Nested
    @DisplayName("Member를 저장 후 같은 로그인 아이디로 회원을 찾았을 때")
    class join_and_findByLoginId_Test {

        private Member saveMember = sampleMember();

        @Test
        @DisplayName("같은 식별자를 가지고 있어야한다.")
        public void idEqualsTest() {
            // given
            memberRepository.save(saveMember);

            // when
            Member findMember = memberRepository.findByLoginId(saveMember.getLoginId()).get();

            // then
            assertThat(findMember.getId()).isEqualTo(saveMember.getId());
        }

        @Test
        @DisplayName("같은 loginId를 가지고 있어야한다.")
        public void loginIdEqualsTest() {
            // given
            memberRepository.save(saveMember);

            // when
            Member findMember = memberRepository.findByLoginId(saveMember.getLoginId()).get();

            // then
            assertThat(findMember.getLoginId()).isEqualTo(saveMember.getLoginId());
        }

        @Test
        @DisplayName("같은 password를 가지고 있어야한다.")
        public void passwordEqualsTest() {
            // given
            memberRepository.save(saveMember);

            // when
            Member findMember = memberRepository.findByLoginId(saveMember.getLoginId()).get();

            // then
            assertThat(findMember.getPassword()).isEqualTo(saveMember.getPassword());
        }

        @Test
        @DisplayName("같은 이름을 가지고 있어야한다.")
        public void nameEqualsTest() {
            // given
            memberRepository.save(saveMember);

            // when
            Member findMember = memberRepository.findByLoginId(saveMember.getLoginId()).get();

            // then
            assertThat(findMember.getName()).isEqualTo(saveMember.getName());
        }

        @Test
        @DisplayName("같은 이메일을 가지고 있어야한다.")
        public void emailEqualsTest() {
            // given
            memberRepository.save(saveMember);

            // when
            Member findMember = memberRepository.findByLoginId(saveMember.getLoginId()).get();

            // then
            assertThat(findMember.getEmail()).isEqualTo(saveMember.getEmail());
        }
    }
}
