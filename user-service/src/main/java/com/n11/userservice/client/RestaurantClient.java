package com.n11.userservice.client;

import com.n11.userservice.dto.RestaurantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "restaurant",
		url = "${restaurant.service.url}",
		configuration = FeignConfig.class)
public interface RestaurantClient {
	@GetMapping("{id}")
	ClientResponse<RestaurantDTO> getRestaurant(@PathVariable String id);
}



