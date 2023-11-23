package com.example.miniproject.domain.cart.entity;

import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
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
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @Column(nullable = false)
    private LocalDate checkinDate;

    @Column(nullable = false)
    private LocalDate checkoutDate;

    private CartItem(
        Member member,
        RoomType roomType,
        LocalDate checkinDate,
        LocalDate checkoutDate
    ) {
        this.member = member;
        this.roomType = roomType;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
    }

    public static CartItem create(Member member, RoomType roomType, LocalDate checkinDate, LocalDate checkoutDate) {
        return new CartItem(member, roomType, checkinDate, checkoutDate);
    }
}
