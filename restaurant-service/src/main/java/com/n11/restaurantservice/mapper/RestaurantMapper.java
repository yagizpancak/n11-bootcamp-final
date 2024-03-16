package com.n11.restaurantservice.mapper;

import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.request.RestaurantSaveRequest;
import com.n11.restaurantservice.request.RestaurantUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {
	RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

	@Mapping(target = "coordinate", source = ".", qualifiedByName = "getSaveCoordinate")
	Restaurant converToRestaurant(RestaurantSaveRequest request);

	@Mapping(target = "coordinate", source = ".", qualifiedByName = "getUpdateCoordinate")
	Restaurant convertUpdateRequestToRestaurant(RestaurantUpdateRequest request);

	@Named("getSaveCoordinate")
	default String getCoordinate(RestaurantSaveRequest request) {
		return request.latitude()+","+request.longitude();
	}

	@Named("getUpdateCoordinate")
	default String getCoordinate(RestaurantUpdateRequest request) {
		return request.latitude()+","+request.longitude();
	}

	@Mapping(target = "latitude", source = ".", qualifiedByName = "getLatitude")
	@Mapping(target = "longitude", source = ".", qualifiedByName = "getLongitude")
	RestaurantDTO convertToRestaurantDTO(Restaurant restaurant);

	@Named("getLatitude")
	default BigDecimal getLatitude(Restaurant restaurant) {
		return BigDecimal.valueOf(Double.parseDouble(restaurant.getCoordinate().split(",")[0]));
	}

	@Named("getLongitude")
	default BigDecimal getLongitude(Restaurant restaurant) {
		return BigDecimal.valueOf(Double.parseDouble(restaurant.getCoordinate().split(",")[1]));
	}

	List<RestaurantDTO> convertToRestaurantDTOs(List<Restaurant> restaurants);
}
