package com.example.miniproject.domain.order.service;

import com.example.miniproject.domain.order.dto.request.OrderItemRegisterRequest;
import com.example.miniproject.domain.order.dto.request.OrderRegisterRequest;
import com.example.miniproject.domain.order.dto.response.OrderRegisterResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderServiceFacade {

    private static final String GET_LOCK = "SELECT GET_LOCK(?, ?)";
    private static final String RELEASE_LOCK = "SELECT RELEASE_LOCK(?)";
    private static final String EXCEPTION_MESSAGE = "LOCK 을 수행하는 중에 오류가 발생하였습니다.";
    private static final int LOCK_TIMEOUT_SECONDS = 3;

    private final OrderService orderService;
    private final DataSource jdbcDataSource;

    public OrderRegisterResponse registerOrder(Long memberId, OrderRegisterRequest request) {

        Set<String> keys = request.orderItems()
            .stream()
            .map(OrderItemRegisterRequest::roomTypeId)
            .map(Object::toString)
            .collect(Collectors.toSet());

        try (Connection connection = jdbcDataSource.getConnection()) {
            try {
                for (String key : keys) {
                    getLock(connection, key);
                }
                return orderService.registerOrder(memberId, request);
            } finally {
                for (String key : keys) {
                    releaseLock(connection, key);
                }
            }
        } catch (SQLException | RuntimeException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private void getLock(
        Connection connection,
        String key
    ) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LOCK)) {
            preparedStatement.setString(1, key);
            preparedStatement.setInt(2, LOCK_TIMEOUT_SECONDS);

            checkResultSet(preparedStatement);
        }
    }

    private void releaseLock(
        Connection connection,
        String key
    ) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(RELEASE_LOCK)) {
            preparedStatement.setString(1, key);

            checkResultSet(preparedStatement);
        }
    }

    private void checkResultSet(
        PreparedStatement preparedStatement
    ) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.next()) {
                throw new RuntimeException(EXCEPTION_MESSAGE);
            }
            int result = resultSet.getInt(1);
            if (result != 1) {
                throw new RuntimeException(EXCEPTION_MESSAGE);
            }
        }
    }
}
