package com.example.miniproject.domain.order.repository;

import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByMemberOrderByIdDesc(Member member);
}
