package com.n11.userservice.entity;

import com.n11.userservice.enums.UserGender;
import com.n11.userservice.general.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User extends BaseEntity {
	@SequenceGenerator(name = "User", sequenceName = "USER_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "User")
	@Id
	private Long id;

	@NotBlank
	@Length(min = 1, max = 100)
	@Column(name = "NAME", length = 100, nullable = false)
	private String name;

	@NotBlank
	@Length(min = 1, max = 100)
	@Column(name = "SURNAME", length = 100, nullable = false)
	private String surname;

	@NotBlank
	@Column(name = "USERNAME", length = 100, nullable = false)
	private String username;

	@Email
	@NotBlank
	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Past
	@NotNull
	@Column(name = "BIRTH_DATE")
	private LocalDate birthDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "GENDER", length = 20)
	private UserGender gender;

	@NotNull
	@Column(name = "LATITUDE", nullable = false)
	private BigDecimal latitude;

	@NotNull
	@Column(name = "LONGITUDE", nullable = false)
	private BigDecimal longitude;
}
