package com.n11.restaurantservice.controller.contract.impl;

import com.n11.restaurantservice.controller.contract.RestaurantControllerContract;
import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.mapper.RestaurantMapper;
import com.n11.restaurantservice.request.RestaurantSaveRequest;
import com.n11.restaurantservice.request.RestaurantUpdateRequest;
import com.n11.restaurantservice.service.RestaurantEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantControllerContractImpl implements RestaurantControllerContract {

	private final RestaurantEntityService restaurantEntityService;
	@Override
	public List<RestaurantDTO> getAllRestaurants() {
		List<Restaurant> restaurantList = restaurantEntityService.findAll();
		return RestaurantMapper.INSTANCE.convertToRestaurantDTOs(restaurantList);
	}

	@Override
	public RestaurantDTO getRestaurantById(String id) {
		Restaurant restaurant = restaurantEntityService.findByIdWithControl(id);
		return RestaurantMapper.INSTANCE.convertToRestaurantDTO(restaurant);
	}

	@Override
	public RestaurantDTO saveRestaurant(RestaurantSaveRequest restaurantSaveRequest) {
		Restaurant restaurant = RestaurantMapper.INSTANCE.converToRestaurant(restaurantSaveRequest);
		restaurant = restaurantEntityService.create(restaurant);
		return RestaurantMapper.INSTANCE.convertToRestaurantDTO(restaurant);
	}

	@Override
	public RestaurantDTO updateRestaurant(RestaurantUpdateRequest restaurantUpdateRequest) {
		Restaurant restaurant = RestaurantMapper.INSTANCE.convertUpdateRequestToRestaurant(restaurantUpdateRequest);
		restaurant = restaurantEntityService.update(restaurant);
		return RestaurantMapper.INSTANCE.convertToRestaurantDTO(restaurant);
	}

	@Override
	public void deleteRestaurant(String id) {
		restaurantEntityService.delete(id);
	}
}