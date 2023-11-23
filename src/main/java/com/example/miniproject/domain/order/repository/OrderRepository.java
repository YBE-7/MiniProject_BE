package com.example.miniproject.domain.order.repository;

import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.order.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByMemberOrderByIdDesc(Member member);
}
