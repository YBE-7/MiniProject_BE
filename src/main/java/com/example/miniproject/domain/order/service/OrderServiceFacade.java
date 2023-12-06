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
    private static final int LOCK_LEASE_TIME = 5;
    private static final String LOCK_NAME_PREFIX = "order_lock_";
    private static final String LOCK_EXCEPTION_MESSAGE = "Lock을 획득하지 못했습니다.";
    private static final String INTERRUPTED_EXCEPTION_MESSAGE = "Lock을 획득하는 동안 인터럽트가 발생했습니다.";

    private final OrderService orderService;
    private final RedissonClient redissonClient;

    public OrderRegisterResponse registerOrder(Long memberId, OrderRegisterRequest request) {
        RLock[] locks = request.orderItems()
            .stream()
            .map(OrderItemRegisterRequest::roomTypeId)
            .distinct()
            .map(key -> redissonClient.getLock(LOCK_NAME_PREFIX + key))
            .toArray(RLock[]::new);

        RLock multiLock = redissonClient.getMultiLock(locks);

        try {
            if (!multiLock.tryLock(
                LOCK_WAIT_TIME,
                LOCK_LEASE_TIME,
                TimeUnit.SECONDS
            )) {
                throw new RuntimeException(LOCK_EXCEPTION_MESSAGE);
            }
            return orderService.registerOrder(memberId, request);
        } catch (InterruptedException ex) {
            throw new RuntimeException(INTERRUPTED_EXCEPTION_MESSAGE, ex);
        } finally {
            multiLock.unlock();
        }
    }
}
