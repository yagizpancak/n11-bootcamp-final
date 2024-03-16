package com.n11.restaurantservice.dto;

import java.math.BigDecimal;

public record RestaurantDistanceDTO(String id,
									String name,
									BigDecimal distance) {
}
