package member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(of = {"id", "loginId", "password", "name", "email"})
public class Member {

    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String email;

    @Builder
    public Member(String loginId, String password, String name, String email) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void initId(Long id) {
        this.id = id;
    }
}
