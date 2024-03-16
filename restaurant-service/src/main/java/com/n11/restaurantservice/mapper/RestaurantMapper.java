package com.n11.restaurantservice.mapper;

import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.request.RestaurantSaveRequest;
import com.n11.restaurantservice.request.RestaurantUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {
	RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

	Restaurant converToRestaurant(RestaurantSaveRequest request);
	Restaurant convertUpdateRequestToRestaurant(RestaurantUpdateRequest request);
	RestaurantDTO convertToRestaurantDTO(Restaurant restaurant);
	List<RestaurantDTO> convertToRestaurantDTOs(List<Restaurant> restaurants);

}
