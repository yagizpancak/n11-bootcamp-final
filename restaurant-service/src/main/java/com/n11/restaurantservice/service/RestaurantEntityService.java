package com.n11.restaurantservice.service;

import com.n11.restaurantservice.dao.RestaurantRepository;
import com.n11.restaurantservice.dto.RestaurantDistanceDTO;
import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.errormessage.ErrorMessage;
import com.n11.restaurantservice.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.*;

@Service
public class RestaurantEntityService {
	private final RestaurantRepository repository;
	@Value("${solr.search.distance}")
	private Double DISTANCE;

	protected RestaurantEntityService(RestaurantRepository repository) {
		this.repository = repository;
	}

	public List<Restaurant> findAll(){
		List<Restaurant> restaurants = new ArrayList<>();
		repository.findAll().forEach(restaurants::add);
		return restaurants;
	}

	public Restaurant findByIdWithControl(String id){
		Optional<Restaurant> optional = repository.findById(id);
		Restaurant entity;
		if (optional.isPresent()) {
			entity = optional.get();
		} else {
			throw new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND);
		}
		return entity;
	}

	public Restaurant update(Restaurant entity){
		this.findByIdWithControl(entity.getId());
		return repository.save(entity);
	}

	public Restaurant create(Restaurant entity){
		return repository.create(entity);
	}

	public void delete(String id){
		repository.deleteById(id);
	}

	public List<RestaurantDistanceDTO> findByDistanceLessThan(BigDecimal latitude, BigDecimal longitude) {
		List<Restaurant> restaurants = repository.findByLocationWithinDistance(latitude.doubleValue(), longitude.doubleValue(), DISTANCE);
		return restaurants.stream()
				.map(restaurant -> new RestaurantDistanceDTO(
					restaurant.getId(),
					restaurant.getName(),
					calculateDistance(latitude, longitude, restaurant.getCoordinate())
			))
				.toList();
	}

	private BigDecimal calculateDistance(BigDecimal latitude, BigDecimal longitude, String coordinate){
		BigDecimal cordLatitude = BigDecimal.valueOf(Double.parseDouble(coordinate.split(",")[0]));
		BigDecimal cordLongitude = BigDecimal.valueOf(Double.parseDouble(coordinate.split(",")[1]));

		double distanceLat = toRadians(cordLatitude.subtract(latitude).doubleValue());
		double distanceLon = toRadians(cordLongitude.subtract(longitude).doubleValue());

		double startLat = toRadians(latitude.doubleValue());
		double endLat = toRadians(cordLatitude.doubleValue());

		double a = pow(sin(distanceLat/2),2) + cos(startLat) * cos(endLat) * pow(sin(distanceLon/2),2);
		double c = 2*atan2(sqrt(a), sqrt(1-a));

		return BigDecimal.valueOf(6371*c);
	}
}
