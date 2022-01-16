package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;


    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")//Order 테이블에 있는 member에 의해서 mapping된거야, 읽기 전용
    private List<Order> orders = new ArrayList<>();
}
