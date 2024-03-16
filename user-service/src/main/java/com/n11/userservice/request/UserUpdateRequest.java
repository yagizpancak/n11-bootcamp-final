package com.n11.userservice.request;

import com.n11.userservice.enums.UserGender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserUpdateRequest(@NotNull Long id,
								@NotBlank @Length(min = 1, max = 100) String name,
								@NotBlank @Length(min = 1, max = 100) String surname,
								@NotBlank String username,
								@NotBlank @Email String email,
								@NotNull @Past LocalDate birthDate,
								@NotNull UserGender gender,
								@NotNull BigDecimal latitude,
								@NotNull BigDecimal longitude) {
}
