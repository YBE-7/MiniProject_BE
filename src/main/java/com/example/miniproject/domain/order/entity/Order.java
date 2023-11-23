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
@Table(name = "orders")
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

    @Column(nullable = false)
    private String paymentMethod;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<OrderItem> orderItems = new ArrayList<>();

    private Order(
        Member member,
        String clientName,
        String clientPhoneNumber,
        String subscriberName,
        String subscriberPhoneNumber,
        String paymentMethod
    ) {
        this.member = member;
        this.totalPrice = 0;
        this.clientName = clientName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.subscriberName = subscriberName;
        this.subscriberPhoneNumber = subscriberPhoneNumber;
        this.paymentMethod = paymentMethod;
    }

    public static Order create(
        Member member,
        String clientName,
        String clientPhoneNumber,
        String subscriberName,
        String subscriberPhoneNumber,
        String paymentMethod
    ) {
        return new Order(member, clientName, clientPhoneNumber, subscriberName, subscriberPhoneNumber, paymentMethod);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        this.totalPrice += orderItem.getRoom().getRoomType().getPrice();
        orderItem.setOrder(this);
    }
}
