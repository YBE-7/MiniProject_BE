package com.example.miniproject.domain.accommodation.repository;

import static org.assertj.core.api.Assertions.*;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.accommodation.entity.AccommodationImage;
import com.example.miniproject.domain.accommodation.entity.Location;
import com.example.miniproject.global.constant.AccommodationType;
import com.example.miniproject.global.constant.Region;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class AccommodationRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    private EntityManager entityManager;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @BeforeEach
    void beforeEach() {
        this.entityManager = testEntityManager.getEntityManager();;
    }

    @DisplayName("숙소가 저장되면, 위치와 이미지도 같이 저장됩니다.")
    @Test
    void accommodation_save_test() {
        //given
        Accommodation accommodation = Accommodation.create(
            AccommodationType.HOTEL,
            Region.BUSAN,
            "킹호텔",
            "제일 좋습니다",
            "조식,와이파이",
            4.7,
            Location.create(
                "서울시 강남구",
                122.124125,
                123.1235426
            ),
            "thumbnailUrl"
        );
        accommodation.addImage(AccommodationImage.create("img1Url"));
        accommodation.addImage(AccommodationImage.create("img2Url"));

        //when
        Accommodation result = accommodationRepository.save(accommodation);

        //then
        assertThat(entityManager.find(Accommodation.class, result.getId()))
            .isNotNull();

        List<AccommodationImage> images = entityManager.createQuery(
                "select ai from AccommodationImage ai", AccommodationImage.class)
            .getResultList();
        assertThat(images.size()).isEqualTo(2);

        Location location = entityManager.createQuery(
                "select l from Location l", Location.class
            )
            .getSingleResult();
        assertThat(location).isNotNull();
        assertThat(location.getAddress()).isEqualTo("서울시 강남구");
    }
}