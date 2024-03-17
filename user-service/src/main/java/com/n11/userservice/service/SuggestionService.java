package com.n11.userservice.service;

import com.n11.userservice.client.ClientResponse;
import com.n11.userservice.client.RestaurantClient;
import com.n11.userservice.dao.ReviewRepository;
import com.n11.userservice.dto.RestaurantDistanceDTO;
import com.n11.userservice.dto.RestaurantSuggestDTO;
import com.n11.userservice.entity.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class SuggestionService {
	private final RestaurantClient restaurantClient;
	private final ReviewRepository repository;

	public SuggestionService(ReviewRepository repository, RestaurantClient restaurantClient) {
		this.repository = repository;
		this.restaurantClient = restaurantClient;
	}


	public List<RestaurantSuggestDTO> getSuggestion(User user) {
		ClientResponse<List<RestaurantDistanceDTO>> response = restaurantClient.findByDistanceLessThan(user.getLatitude(), user.getLongitude());
		List<RestaurantDistanceDTO> restaurants = response.getData();

		return restaurants.stream()
				.map(restaurant -> {
					RestaurantSuggestDTO restaurantSuggest = RestaurantSuggestDTO.builder()
							.id(restaurant.id())
							.name(restaurant.name())
							.distance(restaurant.distance())
							.reviewPoint(findReviewPoint(restaurant.id()))
							.build();
					restaurantSuggest.setTotalPoint(findTotalPoint(
							restaurantSuggest.getReviewPoint(), restaurantSuggest.getDistance()
					));
					return restaurantSuggest;
				})
				.sorted(Comparator.comparing(RestaurantSuggestDTO::getTotalPoint).reversed())
				.limit(3)
				.toList();
	}

	private BigDecimal findTotalPoint(BigDecimal reviewPoint, BigDecimal distance) {
		return BigDecimal.valueOf((reviewPoint.doubleValue()*20)*0.7 + ((10-distance.doubleValue())*10)*0.3);
	}

	private BigDecimal findReviewPoint(String id){
		Double point = repository.findAveragePointsByRestaurantId(id);
		if (point != null){
			return BigDecimal.valueOf(point);
		}
		return BigDecimal.ZERO;
	}
}
