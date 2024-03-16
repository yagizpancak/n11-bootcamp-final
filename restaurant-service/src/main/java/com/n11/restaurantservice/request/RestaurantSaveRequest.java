package com.n11.restaurantservice.request;

import java.math.BigDecimal;

public record RestaurantSaveRequest(String name,
									BigDecimal latitude,
									BigDecimal longitude) {
}
