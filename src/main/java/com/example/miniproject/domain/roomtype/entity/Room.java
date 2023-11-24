package com.example.miniproject.domain.roomtype.entity;

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
        String name
    ) {
        this.name = name;
    }

    public static Room create(
        String name
    ) {
        return new Room(name);
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
}
