package com.n11.userservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RestaurantSuggestDTO {
	private String id;
	private String name;
	private BigDecimal distance;
	private BigDecimal reviewPoint;
	private BigDecimal totalPoint;
}
