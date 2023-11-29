package com.example.miniproject.domain.roomtype.repository;

import com.example.miniproject.domain.accommodation.repository.AccommodationRepositoryCustom;
import com.example.miniproject.domain.accommodation.repository.AccommodationRepositoryCustomImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class QueryDslTestConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public RoomTypeRepositoryCustom roomTypeRepositoryCustom() {
        return new RoomTypeRepositoryCustomImpl(jpaQueryFactory());
    }

    @Bean
    public AccommodationRepositoryCustom accommodationRepositoryCustom() {
        return new AccommodationRepositoryCustomImpl(jpaQueryFactory());
    }
}
