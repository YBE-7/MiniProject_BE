package com.example.miniproject.domain.roomtype.entity;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import com.example.miniproject.domain.room.entity.Room;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "room_type")
@Entity
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "accommodation_id", referencedColumnName = "id")
    private Accommodation accommodation;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private String introduction;

    @OneToMany(mappedBy = "roomType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "roomType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<RoomTypeImage> images = new ArrayList<>();

    private RoomType(
        Accommodation accommodation,
        String name,
        Integer price,
        Integer capacity,
        String introduction
    ) {
        this.accommodation = accommodation;
        accommodation.addRoomType(this);
        this.name = name;
        this.price = price;
        this.capacity = capacity;
        this.introduction = introduction;
    }

    public static RoomType create(
        Accommodation accommodation,
        String name,
        Integer price,
        Integer capacity,
        String introduction
    ) {
        return new RoomType(accommodation, name, price, capacity, introduction);
    }

    public void addImage(RoomTypeImage image) {
        images.add(image);
        image.setRoomType(this);
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }
}
