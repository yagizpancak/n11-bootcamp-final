package com.n11.userservice.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class RestaurantSuggestDTO {
	private String id;
	private String name;
	private BigDecimal distance;
	private BigDecimal reviewPoint;
	private BigDecimal totalPoint;
}
