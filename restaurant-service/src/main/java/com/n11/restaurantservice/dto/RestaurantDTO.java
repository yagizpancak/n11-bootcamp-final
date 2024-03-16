package com.n11.restaurantservice.dto;

import java.math.BigDecimal;

public record RestaurantDTO(String id,
						   String name,
						   BigDecimal latitude,
						   BigDecimal longitude) {
}
