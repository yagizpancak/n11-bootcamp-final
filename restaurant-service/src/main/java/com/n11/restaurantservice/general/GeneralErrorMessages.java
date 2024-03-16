package com.n11.restaurantservice.general;

import java.time.LocalDateTime;

public record GeneralErrorMessages(LocalDateTime date, String message, String description) {
}
