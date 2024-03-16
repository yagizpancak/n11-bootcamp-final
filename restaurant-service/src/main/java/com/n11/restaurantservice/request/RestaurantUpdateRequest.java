package com.n11.restaurantservice.request;

import java.math.BigDecimal;

public record RestaurantUpdateRequest(String id,
									  String name,
									  BigDecimal latitude,
									  BigDecimal longitude) {
}
