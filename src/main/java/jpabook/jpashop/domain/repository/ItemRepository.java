package jpabook.jpashop.domain.repository;


import jpabook.jpashop.domain.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;


    public void save(Item item){
        if(item.getId() == null){//id값이 없다- > 새로 생성한 객체
            em.persist(item);// 신규로 저장해주면 되고
        }else{
            em.merge(item);//아니면 업데이트로 이해하면 됨
        }
    }
    public Item findOne(Long id){
        return em.find(Item.class, id);
    }
    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }
}
