package com.example.miniproject.domain.cart.entity;

import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cart_item")
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false)
    private LocalDate checkinDate;

    @Column(nullable = false)
    private LocalDate checkoutDate;

    private CartItem(
        Member member,
        Room room,
        LocalDate checkinDate,
        LocalDate checkoutDate
    ) {
        this.member = member;
        this.room = room;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
    }

    public static CartItem create(Member member, Room room, LocalDate checkinDate, LocalDate checkoutDate) {
        return new CartItem(member, room, checkinDate, checkoutDate);
    }
}
