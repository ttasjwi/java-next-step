package member.service;

import exception.DuplicateLoginIdJoinException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import member.domain.Member;
import member.dto.MemberJoinRequestDto;
import member.repository.MemberRepository;

@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private static MemberService singletonInstance;

    private final MemberRepository memberRepository;

    public static MemberService getSingletonInstance(MemberRepository memberRepository) {
        if (singletonInstance == null) {
            singletonInstance = new MemberService(memberRepository);
            return singletonInstance;
        }
        return singletonInstance;
    }

    public Long join(MemberJoinRequestDto memberJoinRequestDto) {
        Member member = memberJoinRequestDto.toEntity();
        validateDuplicateLoginId(member.getLoginId());
        memberRepository.save(member);

        log.info("Member Join = {}", member);
        return member.getId();
    }

    private void validateDuplicateLoginId(String loginId) {
        memberRepository.findByLoginId(loginId)
                .ifPresent(duplicateMember -> {
                    throw new DuplicateLoginIdJoinException();
                });
    }

}
