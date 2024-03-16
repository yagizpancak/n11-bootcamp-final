package com.n11.userservice.request;

import com.n11.userservice.enums.ReviewRate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

public record ReviewSaveRequest(@NotNull Long userId,
								@UUID String restaurantId,
								@NotNull ReviewRate score,
								@NotBlank String text) {
}
