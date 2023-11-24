package com.example.miniproject.domain.roomtype.repository;

import com.example.miniproject.domain.roomtype.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository
    extends JpaRepository<RoomType, Long>, RoomTypeRepositoryCustom {

}
