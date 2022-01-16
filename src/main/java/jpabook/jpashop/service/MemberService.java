package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)// 읽기 전용으로 처리하여 성능 최적화, 읽기가 2개고 쓰기가 한개라서 맨 위에 readOnly로 처리
@RequiredArgsConstructor // final 만 있는 필드만 가지고 생성자 자동 생성해줌
public class MemberService {


    private final MemberRepository memberRepository;//field injection -final 하는걸 추천

//    @Autowired//Member Injection -setter injection -> 개발하는 중간에 바꿀수 있다 - 단점
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

//    @Autowired // 생성자 injection - 중간에 바꿀 수 없음 -> 궁극적으로 이거 사용, 생성자가 하나일때는 Autowired자동으로 해줌
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    } -> LOMBOK사용해서 직접 생성 필요 없음


//회원 가입

    @Transactional//JPA의 모든 데이터 변경, 로직은 transaction 안에서 사용되어야 하므로 어노테이션이 있어야 한다. 쓰기에는 readonly넣으면 안 됨, 따로 설정해 우선권
    public Long join(Member member){

        validateDuplicateMember(member);//중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");//멀티 쓰레드 현상을 고려해 memberName을 UniQue 제한 조건을 실무에선 걸어야 함
        }
    }


    //회원 전체 조회


    //@Transactional(readOnly = true) 맨 위에서 처리해 줘서 주석 처
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    //@Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}

