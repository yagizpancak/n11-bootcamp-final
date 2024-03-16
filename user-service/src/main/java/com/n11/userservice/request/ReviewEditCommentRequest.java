package com.n11.userservice.request;

import jakarta.validation.constraints.NotBlank;

public record ReviewEditCommentRequest(@NotBlank String text) {
}
