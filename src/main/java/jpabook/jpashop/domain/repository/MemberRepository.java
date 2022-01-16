package jpabook.jpashop.domain.repository;


import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    //@PersistenceContext - >원래는 이거 사용해야 하지만
    //@Autowired // Spring boot사용해서 이걸로 사용 가능
    private final EntityManager em; //repository에 Entitymanager를 생성자로 injection한 것

    public void save(Member member){
        em.persist(member);//영속성 context 에 member Entity에 넣음
    }


    public Member findOne(Long id){
        return em.find(Member.class, id);//단건 조회,(type, PK)
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)//JPQL, from 의 데상이 table이 아니고 Entity가 됨
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class) // 특정 이름의 회원을 찾음
                .setParameter("name",name)
                .getResultList();
    }


}
