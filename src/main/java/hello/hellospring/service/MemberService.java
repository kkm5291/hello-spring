package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//서비스 클래스는 비즈니스적인 용어로 메서드를 만들것
//@Service //스프링에 올라올때 스프링이 서비스라고 알아서 등록해줌

@Transactional
public class MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

//    @Autowired //구현체를 자동으로 넣어준다? (7분 44초 자동의존관계)
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원 가입
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복회원검증

        memberRepository.save(member); // 리포지터리 save기능을 통해 DB에 저장
        return member.getId();

        //같은 이름이 있는 중복 회원 X
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
