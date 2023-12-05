package com.example.miniproject.domain.order.service;

import com.example.miniproject.domain.order.dto.request.OrderItemRegisterRequest;
import com.example.miniproject.domain.order.dto.request.OrderRegisterRequest;
import com.example.miniproject.domain.order.dto.response.OrderRegisterResponse;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OrderServiceFacade {

    private static final int LOCK_WAIT_TIME = 3;
    private static final int LOCK_LEASE_TIME = 1;
    private static final String LOCK_NAME_PREFIX = "order_lock_";

    private final OrderService orderService;
    private final RedissonClient redissonClient;

    public OrderRegisterResponse registerOrder(Long memberId, OrderRegisterRequest request) {
        RLock[] locks = request.orderItems()
            .stream()
            .map(OrderItemRegisterRequest::roomTypeId)
            .map(Object::toString)
            .map(key -> redissonClient.getLock(LOCK_NAME_PREFIX + key))
            .toArray(RLock[]::new);

        try {
            if (redissonClient.getMultiLock(locks).tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS)) {
                try {
                    return orderService.registerOrder(memberId, request);
                } finally {
                    redissonClient.getMultiLock(locks).unlock();
                }
            } else {
                throw new RuntimeException("Lock을 획득하지 못했습니다.");
            }
        } catch (InterruptedException ex) {
            throw new RuntimeException("Lock을 획득하는 동안 인터럽트가 발생했습니다.", ex);
        }
    }
}
