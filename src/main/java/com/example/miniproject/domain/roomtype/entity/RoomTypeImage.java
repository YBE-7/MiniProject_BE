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
@Table(name = "room_type_image")
@Entity
public class RoomTypeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", referencedColumnName = "id")
    private RoomType roomType;

    @Column(nullable = false)
    private String url;

    private RoomTypeImage(
        String url
    ) {
        this.url = url;
    }

    public static RoomTypeImage create(
        String url
    ) {
        return new RoomTypeImage(url);
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
}