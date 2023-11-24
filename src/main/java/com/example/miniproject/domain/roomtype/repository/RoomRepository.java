package com.example.miniproject.domain.roomtype.repository;

import com.example.miniproject.domain.roomtype.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
