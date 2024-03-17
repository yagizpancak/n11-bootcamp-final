package com.n11.userservice.dto;

import java.math.BigDecimal;

public record RestaurantDistanceDTO(String id,
									String name,
									BigDecimal distance) {
}
