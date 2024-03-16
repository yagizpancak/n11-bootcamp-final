package com.n11.restaurantservice.controller.contract;

import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.request.RestaurantSaveRequest;
import com.n11.restaurantservice.request.RestaurantUpdateRequest;

import java.util.List;

public interface RestaurantControllerContract {
	List<RestaurantDTO> getAllRestaurants();
	RestaurantDTO getRestaurantById(String id);
	RestaurantDTO saveRestaurant(RestaurantSaveRequest restaurantSaveRequest);
	RestaurantDTO updateRestaurant(RestaurantUpdateRequest restaurantUpdateRequest);
	void deleteRestaurant(String id);
}
