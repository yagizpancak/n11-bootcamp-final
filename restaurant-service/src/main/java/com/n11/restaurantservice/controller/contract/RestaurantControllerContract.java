package com.n11.restaurantservice.controller.contract;

import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.dto.RestaurantDistanceDTO;
import com.n11.restaurantservice.request.RestaurantSaveRequest;
import com.n11.restaurantservice.request.RestaurantUpdateRequest;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantControllerContract {
	List<RestaurantDTO> getAllRestaurants();
	RestaurantDTO getRestaurantById(String id);
	RestaurantDTO saveRestaurant(RestaurantSaveRequest restaurantSaveRequest);
	RestaurantDTO updateRestaurant(RestaurantUpdateRequest restaurantUpdateRequest);
	void deleteRestaurant(String id);
	List<RestaurantDistanceDTO> findByDistanceLessThan(BigDecimal latitude, BigDecimal longitude);
}
