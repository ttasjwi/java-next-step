package member.dto;

import lombok.Builder;
import lombok.Getter;
import member.domain.Member;

@Getter
public class MemberJoinRequestDto {

    private String loginId;
    private String password;
    private String name;
    private String email;

    @Builder
    public MemberJoinRequestDto(String loginId, String password, String name, String email) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Member toEntity() {
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .email(email)
                .build();
    }
}
