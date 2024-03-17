package com.n11.restaurantservice.controller.contract.impl;

import com.n11.restaurantservice.controller.contract.RestaurantControllerContract;
import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.dto.RestaurantDistanceDTO;
import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.general.KafkaProducerService;
import com.n11.restaurantservice.mapper.RestaurantMapper;
import com.n11.restaurantservice.request.RestaurantSaveRequest;
import com.n11.restaurantservice.request.RestaurantUpdateRequest;
import com.n11.restaurantservice.service.RestaurantEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantControllerContractImpl implements RestaurantControllerContract {
	private final KafkaProducerService kafkaProducerService;
	private final RestaurantEntityService restaurantEntityService;
	@Override
	public List<RestaurantDTO> getAllRestaurants() {
		List<Restaurant> restaurantList = restaurantEntityService.findAll();

		kafkaProducerService.sendMessage("infoLog", restaurantList.toString());
		return RestaurantMapper.INSTANCE.convertToRestaurantDTOs(restaurantList);
	}

	@Override
	public RestaurantDTO getRestaurantById(String id) {
		Restaurant restaurant = restaurantEntityService.findByIdWithControl(id);

		kafkaProducerService.sendMessage("infoLog", restaurant.toString());
		return RestaurantMapper.INSTANCE.convertToRestaurantDTO(restaurant);
	}

	@Override
	public RestaurantDTO saveRestaurant(RestaurantSaveRequest restaurantSaveRequest) {
		Restaurant restaurant = RestaurantMapper.INSTANCE.converToRestaurant(restaurantSaveRequest);
		restaurant = restaurantEntityService.create(restaurant);

		kafkaProducerService.sendMessage("infoLog", restaurant.toString());
		return RestaurantMapper.INSTANCE.convertToRestaurantDTO(restaurant);
	}

	@Override
	public RestaurantDTO updateRestaurant(RestaurantUpdateRequest restaurantUpdateRequest) {
		Restaurant restaurant = RestaurantMapper.INSTANCE.convertUpdateRequestToRestaurant(restaurantUpdateRequest);
		restaurant = restaurantEntityService.update(restaurant);

		kafkaProducerService.sendMessage("infoLog", restaurant.toString());
		return RestaurantMapper.INSTANCE.convertToRestaurantDTO(restaurant);
	}

	@Override
	public void deleteRestaurant(String id) {
		kafkaProducerService.sendMessage("infoLog", String.format("Restaurant deleted with id: %s", id));
		restaurantEntityService.delete(id);
	}

	@Override
	public List<RestaurantDistanceDTO> findByDistanceLessThan(BigDecimal latitude, BigDecimal longitude) {
		return restaurantEntityService.findByDistanceLessThan(latitude, longitude);
	}
}
