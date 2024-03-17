package com.n11.userservice.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewEditScoreRequest(@NotNull @Min(1) @Max(5) Integer score) {
}
