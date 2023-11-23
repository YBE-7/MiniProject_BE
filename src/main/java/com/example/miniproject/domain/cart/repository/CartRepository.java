package com.example.miniproject.domain.cart.repository;

import com.example.miniproject.domain.cart.entity.CartItem;
import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {

    Long countByMemberAndRoomTypeAndCheckinDateAndCheckoutDate(
        Member member,
        RoomType roomType,
        LocalDate checkinDate,
        LocalDate checkoutDate
    );

    List<CartItem> findAllByMember(Member member);

    void deleteAllByMember(Member member);
}
