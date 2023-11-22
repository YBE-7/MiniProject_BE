package com.example.miniproject.domain.roomtype.repository;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.roomtype.entity.RoomType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository
    extends JpaRepository<RoomType, Long>, RoomTypeRepositoryCustom {

    List<RoomType> findByAccommodationAndCapacityGreaterThanEqual(Accommodation accommodation, Integer capacity);

}
