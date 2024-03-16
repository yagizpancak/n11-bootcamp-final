package com.n11.userservice.request;

import com.n11.userservice.enums.UserGender;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserUpdateRequest(Long id,
								String name,
								String surname,
								String username,
								String email,
								LocalDate birthDate,
								UserGender gender,
								BigDecimal latitude,
								BigDecimal longitude) {
}
