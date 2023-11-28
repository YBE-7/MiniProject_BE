package com.example.miniproject.domain.order.entity;

import com.example.miniproject.domain.roomtype.entity.Room;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item")
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @Column(nullable = false)
    private LocalDate checkinDate;

    @Column(nullable = false)
    private LocalDate checkoutDate;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private Integer price;

    private OrderItem(
        Order order,
        Room room,
        LocalDate checkinDate,
        LocalDate checkoutDate,
        String code,
        Integer price
    ) {
        this.order = order;
        this.room = room;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.code = code;
        this.price = price;
    }

    public static OrderItem create(
        Order order,
        Room room,
        LocalDate checkinDate,
        LocalDate checkoutDate,
        String code,
        Integer price
    ) {
        return new OrderItem(order, room, checkinDate, checkoutDate, code, price);
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
