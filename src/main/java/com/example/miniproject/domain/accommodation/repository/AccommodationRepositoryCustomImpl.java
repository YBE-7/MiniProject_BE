package com.example.miniproject.domain.accommodation.repository;

import static com.example.miniproject.domain.accommodation.entity.QAccommodation.accommodation;
import static com.example.miniproject.domain.order.entity.QOrderItem.*;
import static com.example.miniproject.domain.room.entity.QRoom.room;
import static com.example.miniproject.global.constant.SearchOrderCondition.PRICE_ASC;
import static com.example.miniproject.global.constant.SearchOrderCondition.PRICE_DESC;

import com.example.miniproject.domain.accommodation.dto.request.AccommodationSearchCondition;
import com.example.miniproject.domain.accommodation.dto.response.AccommodationResponse;
import com.example.miniproject.domain.order.entity.QOrderItem;
import com.example.miniproject.global.constant.AccommodationType;
import com.example.miniproject.global.constant.Region;
import com.example.miniproject.global.constant.SearchOrderCondition;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccommodationRepositoryCustomImpl implements AccommodationRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<AccommodationResponse> findBySearchCondition(AccommodationSearchCondition condition) {

        NumberExpression<Integer> minPrice = room.price.min();

        return query
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
            .from(room)
            .join(room.accommodation, accommodation)
            .where(
                hasStock(),
                typeEqual(condition.type()),
                regionEqual(condition.region()),
                dateBefore(condition.checkinDate()),
                dateAfter(condition.checkoutDate()),
                capacityGreaterOrEqual(condition.capacity())
            )
            .groupBy(accommodation)
            .orderBy(createOrderSpecifier(condition.order(), minPrice))
            .fetch();
    }

    private BooleanExpression hasStock() {
        return room.stock.gt(1);
    }

    private BooleanExpression typeEqual(AccommodationType type) {
        return (type == null) ? null : accommodation.type.eq(type);
    }

    private BooleanExpression regionEqual(Region region) {
        return (region == null) ? null : accommodation.region.eq(region);
    }

    private BooleanExpression dateBefore(LocalDate date) {
        return (date == null) ? null : room.startDate.loe(date);
    }

    private BooleanExpression dateAfter(LocalDate date) {
        return (date == null) ? null : room.endDate.goe(date);
    }

    private BooleanExpression capacityGreaterOrEqual(Integer capacity) {
        return (capacity == null) ? null : room.capacity.goe(capacity);
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
