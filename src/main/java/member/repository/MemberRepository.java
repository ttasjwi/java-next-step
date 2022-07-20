package member.repository;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import member.domain.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class MemberRepository {

    private final Map<Long, Member> store = Maps.newHashMap();
    private final AtomicLong sequence = new AtomicLong();

    /**
     * 회원 저장
     */
    public Long save(Member member) {
        Long id = sequence.incrementAndGet();
        member.initId(id);
        store.put(member.getId(), member);
        return id;
    }

    /**
     * 식별자로 회원 조회
     */
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    /**
     * 로그인아이디로 조회
     */
    public Optional<Member> findByLoginId(String loginId) {
        return store.values().stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .findFirst();
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
