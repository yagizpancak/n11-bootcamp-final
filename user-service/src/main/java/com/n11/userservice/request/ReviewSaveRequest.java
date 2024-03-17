package com.n11.userservice.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

public record ReviewSaveRequest(@NotNull Long userId,
								@UUID String restaurantId,
								@NotNull @Min(1) @Max(5) Integer score,
								@NotBlank String text) {
}
