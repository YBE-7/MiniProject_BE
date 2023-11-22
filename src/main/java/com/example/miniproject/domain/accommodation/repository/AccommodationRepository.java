package com.example.miniproject.domain.accommodation.repository;

import com.example.miniproject.domain.accommodation.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long>{

}
