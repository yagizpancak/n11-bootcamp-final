package com.n11.userservice.entity;

import com.n11.userservice.enums.ReviewRate;
import com.n11.userservice.general.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "REVIEW")
public class Review extends BaseEntity {
	@SequenceGenerator(name = "Review", sequenceName = "REVIEW_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Review")
	@Id
	private Long id;

	@NotNull
	@Column(name = "USER_ID", nullable = false)
	private Long userId;

	@UUID
	@Column(name = "RESTAURANT_ID", nullable = false)
	private String restaurantId;

	@NotNull
	@Column(name = "SCORE", nullable = false)
	@Enumerated(EnumType.STRING)
	private ReviewRate score;

	@NotBlank
	@Column(name = "TEXT", length = 1000)
	private String text;
}
