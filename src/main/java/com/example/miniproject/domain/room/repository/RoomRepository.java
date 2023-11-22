package com.example.miniproject.domain.room.repository;

import com.example.miniproject.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
