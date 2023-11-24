package com.example.miniproject.domain.roomtype.repository;

import static com.example.miniproject.domain.order.entity.QOrderItem.orderItem;
import static com.example.miniproject.domain.roomtype.entity.QRoom.room;

import com.example.miniproject.domain.roomtype.entity.QRoom;
import com.example.miniproject.domain.roomtype.entity.Room;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomTypeRepositoryCustomImpl implements RoomTypeRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Long getStockBySchedule(
        RoomType roomType,
        LocalDate checkinDate,
        LocalDate checkoutDate
    ) {
        return query
            .select(room.count())
            .from(room)
            .where(
                room.roomType.eq(roomType),
                validRooms(checkinDate, checkoutDate)
            )
            .fetchOne();
    }

    @Override
    public List<Room> findAvailableRooms(
        RoomType roomType,
        LocalDate checkinDate,
        LocalDate checkoutDate
    ) {
        return query
            .selectFrom(room)
            .where(
                room.roomType.eq(roomType),
                validRooms(checkinDate, checkoutDate)
            )
            .fetch();
    }

    private BooleanExpression validRooms(LocalDate checkinDate, LocalDate checkoutDate) {
        QRoom subRoom = new QRoom("subRoom");
        return room
            .notIn(
                JPAExpressions
                    .selectFrom(subRoom)
                    .join(orderItem).on(orderItem.room.eq(subRoom))
                    .where(
                        overlapped(checkinDate, checkoutDate)
                    )
            );
    }

    private BooleanExpression overlapped(LocalDate checkinDate, LocalDate checkoutDate) {
        return orderItem.checkinDate.between(checkinDate, checkoutDate.minusDays(1)).or(
            orderItem.checkoutDate.between(checkinDate.plusDays(1), checkoutDate));
    }
}
