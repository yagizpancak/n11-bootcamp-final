package com.n11.userservice.client;

import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.dto.RestaurantDistanceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.math.BigDecimal;
import java.util.List;

@FeignClient(value = "restaurant",
		url = "${restaurant.service.url}",
		configuration = FeignConfig.class)
public interface RestaurantClient {
	@GetMapping("/{id}")
	ClientResponse<RestaurantDTO> getRestaurant(@PathVariable String id);

	@GetMapping("/distance-lt")
	ClientResponse<List<RestaurantDistanceDTO>> findByDistanceLessThan(@RequestParam BigDecimal latitude,
													   @RequestParam BigDecimal longitude);
}



