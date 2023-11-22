package com.example.miniproject.domain.room.entity;

import com.example.miniproject.domain.roomtype.entity.RoomType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "room")
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", referencedColumnName = "id")
    private RoomType roomType;

    @Column(nullable = false)
    private String name;

    private Room(
        RoomType roomType,
        String name
    ) {
        this.roomType = roomType;
        roomType.addRoom(this);
        this.name = name;
    }

    public static Room create(
        RoomType roomType,
        String name
    ) {
        return new Room(roomType, name);
    }
}
