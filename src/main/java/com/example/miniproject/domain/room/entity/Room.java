package com.example.miniproject.domain.room.entity;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    @JoinColumn(name = "accommodation_id", referencedColumnName = "id")
    private Accommodation accommodation;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private String introduction;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RoomImage> images = new ArrayList<>();

    private Room(
        Accommodation accommodation,
        Integer price,
        String name,
        Integer capacity,
        String introduction,
        Integer stock,
        LocalDate startDate,
        LocalDate endDate
    ) {
        this.accommodation = accommodation;
        accommodation.addRoom(this);
        this.price = price;
        this.name = name;
        this.capacity = capacity;
        this.introduction = introduction;
        this.stock = stock;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Room create(
        Accommodation accommodation,
        Integer price,
        String name,
        Integer capacity,
        String introduction,
        Integer stock,
        LocalDate startDate,
        LocalDate endDate
    ) {
        return new Room(accommodation, price, name, capacity, introduction, stock, startDate, endDate);
    }

    public void addImage(RoomImage image) {
        images.add(image);
        image.setRoom(this);
    }
}
