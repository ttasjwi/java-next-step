package member.service;

import exception.DuplicateLoginIdJoinException;
import lombok.RequiredArgsConstructor;
import member.domain.Member;
import member.dto.MemberJoinRequestDto;
import member.repository.MemberRepository;

@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(MemberJoinRequestDto memberJoinRequestDto) {
        Member member = memberJoinRequestDto.toEntity();
        validateDuplicateLoginId(member.getLoginId());
        return memberRepository.save(member);
    }

    private void validateDuplicateLoginId(String loginId) {
        memberRepository.findByLoginId(loginId)
                .ifPresent(duplicateMember -> {
                    throw new DuplicateLoginIdJoinException();
                });
    }

}
