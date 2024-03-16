package com.n11.userservice.dto;

import com.n11.userservice.enums.ReviewRate;

public record ReviewDTO(Long id,
						Long userId,
						String restaurantId,
						String restaurantName,
						ReviewRate score,
						String text){
}
