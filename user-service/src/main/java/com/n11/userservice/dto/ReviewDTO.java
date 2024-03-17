package com.n11.userservice.dto;

public record ReviewDTO(Long id,
						Long userId,
						String restaurantId,
						String restaurantName,
						Integer score,
						String text){
}
