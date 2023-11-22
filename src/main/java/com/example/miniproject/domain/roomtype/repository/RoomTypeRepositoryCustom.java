package com.example.miniproject.domain.roomtype.repository;

import com.example.miniproject.domain.roomtype.dto.request.RoomTypeSearchCondition;
import com.example.miniproject.domain.roomtype.entity.RoomType;

public interface RoomTypeRepositoryCustom {
    Long findStockBySearchCondition(RoomType roomType, RoomTypeSearchCondition condition);
}
