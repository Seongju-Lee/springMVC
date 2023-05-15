package sj.springMVC.domain.member;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되지 않음, 실무에선 ConcurrentHashMap, AtomicLong
 */
public class MemberRepository {

    private Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }

    // 해당 클래스는 싱글톤 객체이기 때문에, 아무나 생성 못하도록 생성자 private
    private MemberRepository() { }


    public Member save(Member member) {

        member.setId(++sequence);
        store.put(+sequence, member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
