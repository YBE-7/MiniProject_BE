package com.example.miniproject.domain.accommodation.repository;

import static com.example.miniproject.domain.accommodation.entity.QAccommodation.accommodation;
import static com.example.miniproject.domain.order.entity.QOrderItem.*;
import static com.example.miniproject.domain.room.entity.QRoom.room;
import static com.example.miniproject.domain.roomtype.entity.QRoomType.roomType;
import static com.example.miniproject.global.constant.SearchOrderCondition.PRICE_ASC;
import static com.example.miniproject.global.constant.SearchOrderCondition.PRICE_DESC;

import com.example.miniproject.domain.accommodation.dto.request.AccommodationSearchCondition;
import com.example.miniproject.domain.accommodation.dto.response.AccommodationResponse;
import com.example.miniproject.domain.room.entity.QRoom;
import com.example.miniproject.global.constant.AccommodationType;
import com.example.miniproject.global.constant.Region;
import com.example.miniproject.global.constant.SearchOrderCondition;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class AccommodationRepositoryCustomImpl implements AccommodationRepositoryCustom {

    private static final int DEFAULT_PAGE_OFFSET = 0;
    private static final int DEFAULT_PAGE_SIZE = 8;
    
    private final JPAQueryFactory query;

    @Override
    public Page<AccommodationResponse> findBySearchCondition(AccommodationSearchCondition condition) {

        NumberExpression<Integer> minPrice = roomType.price.min();
        int offset = (condition.page() == null) ? DEFAULT_PAGE_OFFSET : condition.page() - 1;
        int limit = (condition.size() == null) ? DEFAULT_PAGE_SIZE : condition.size();
        Pageable pageable = PageRequest.of(offset, limit);
        int totalCount = getTotalCount(condition);

        List<AccommodationResponse> accommodations = query
            .select(
                Projections.constructor(
                    AccommodationResponse.class,
                    accommodation.id,
                    accommodation.name,
                    accommodation.star,
                    minPrice,
                    accommodation.thumbnailUrl
                )
            )
            .from(accommodation)
            .join(accommodation.roomTypes, roomType)
            .join(roomType.rooms, room)
            .where(
                typeEqual(condition.type()),
                regionEqual(condition.region()),
                capacityGreaterOrEqual(condition.capacity()),
                validRooms(condition.checkinDate(), condition.checkoutDate())
            )
            .groupBy(accommodation.id)
            .orderBy(createOrderSpecifier(condition.order(), minPrice))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(accommodations, pageable, totalCount);
    }

    private int getTotalCount(AccommodationSearchCondition condition) {
        return query
            .select(accommodation)
            .from(accommodation)
            .join(accommodation.roomTypes, roomType)
            .join(roomType.rooms, room)
            .where(
                typeEqual(condition.type()),
                regionEqual(condition.region()),
                capacityGreaterOrEqual(condition.capacity()),
                validRooms(condition.checkinDate(), condition.checkoutDate())
            )
            .groupBy(accommodation.id)
            .fetch()
            .size();
    }

    private BooleanExpression validRooms(LocalDate checkinDate, LocalDate checkoutDate) {
        if (checkinDate == null || checkoutDate == null) {
            return null;
        }
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

    private BooleanExpression typeEqual(AccommodationType type) {
        return (type == null) ? null : accommodation.type.eq(type);
    }

    private BooleanExpression regionEqual(Region region) {
        return (region == null) ? null : accommodation.region.eq(region);
    }

    private BooleanExpression capacityGreaterOrEqual(Integer capacity) {
        return (capacity == null) ? null : roomType.capacity.goe(capacity);
    }

    private OrderSpecifier<?> createOrderSpecifier(
        SearchOrderCondition order,
        NumberExpression<Integer> minPrice
    ) {
        if (order == PRICE_ASC) {
            return minPrice.asc();
        }
        if (order == PRICE_DESC) {
            return minPrice.desc();
        }
        return accommodation.star.desc();
    }
}
