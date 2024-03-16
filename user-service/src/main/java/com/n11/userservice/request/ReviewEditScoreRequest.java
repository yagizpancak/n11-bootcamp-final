package com.n11.userservice.request;

import com.n11.userservice.enums.ReviewRate;
import jakarta.validation.constraints.NotNull;

public record ReviewEditScoreRequest(@NotNull ReviewRate score) {
}
