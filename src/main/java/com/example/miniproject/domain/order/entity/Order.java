package com.example.miniproject.domain.order.entity;

import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.global.entity.BaseTimeEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order")
@Entity
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private String clientPhoneNumber;

    @Column(nullable = false)
    private String subscriberName;

    @Column(nullable = false)
    private String subscriberPhoneNumber;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<OrderItem> orderItems = new ArrayList<>();

    private Order(
        Member member,
        Integer totalPrice,
        String clientName,
        String clientPhoneNumber,
        String subscriberName,
        String subscriberPhoneNumber
    ) {
        this.member = member;
        this.totalPrice = totalPrice;
        this.clientName = clientName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.subscriberName = subscriberName;
        this.subscriberPhoneNumber = subscriberPhoneNumber;
    }

    public static Order create(
            Member member,
            Integer totalPrice,
            String clientName,
            String clientPhoneNumber,
            String subscriberName,
            String subscriberPhoneNumber
    ) {
        return new Order(member, totalPrice, clientName, clientPhoneNumber, subscriberName, subscriberPhoneNumber);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
