package com.example.miniproject.domain.order.entity;

import com.example.miniproject.domain.room.entity.Room;
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

    private OrderItem(
        Room room,
        LocalDate checkinDate,
        LocalDate checkoutDate
    ) {
        this.room = room;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
    }

    public static OrderItem create(
        Room room,
        LocalDate checkinDate,
        LocalDate checkoutDate
    ) {
        return new OrderItem(room, checkinDate, checkoutDate);
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
