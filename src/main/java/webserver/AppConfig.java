package webserver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import member.repository.MemberRepository;
import member.service.MemberService;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConfig {

    private static AppConfig singletonInstance;

    public static AppConfig getSingletonInstance() {
        if (singletonInstance == null) {
            singletonInstance = new AppConfig();
            return singletonInstance;
        }
        return singletonInstance;
    }

    public MemberRepository memberRepository() {
        return MemberRepository.getSingletonInstance();
    }

    public MemberService memberService() {
        return MemberService.getSingletonInstance(memberRepository());
    }

}
