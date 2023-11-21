package com.example.miniproject.domain.accommodation.entity;

import com.example.miniproject.domain.room.entity.Room;
import com.example.miniproject.global.constant.AccommodationType;
import com.example.miniproject.global.constant.Region;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "accommodation")
@Entity
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AccommodationType type;

    @Enumerated(EnumType.STRING)
    private Region region;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private String introduction;

    @Lob
    @Column(nullable = false)
    private String service;

    @Column(nullable = false)
    private Double star;

    @Column(nullable = false)
    private String thumbnailUrl;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<AccommodationImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Room> rooms = new ArrayList<>();

    private Accommodation(
        AccommodationType type,
        Region region,
        String name,
        String introduction,
        String service,
        Double star,
        Location location,
        String thumbnailUrl
    ) {
        this.type = type;
        this.region = region;
        this.name = name;
        this.introduction = introduction;
        this.service = service;
        this.star = star;
        this.location = location;
        this.thumbnailUrl = thumbnailUrl;
    }

    public static Accommodation create(
        AccommodationType type,
        Region region,
        String name,
        String introduction,
        String service,
        Double star,
        Location location,
        String thumbnailUrl
    ) {
        return new Accommodation(type, region, name, introduction, service, star, location, thumbnailUrl);
    }

    public void addImage(AccommodationImage image) {
        images.add(image);
        image.setAccommodation(this);
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }
}
