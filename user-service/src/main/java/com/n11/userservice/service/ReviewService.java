package com.n11.userservice.service;

import com.n11.userservice.client.RestaurantClient;
import com.n11.userservice.dao.ReviewRepository;
import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.dto.ReviewDTO;
import com.n11.userservice.entity.Review;
import com.n11.userservice.errormessage.ErrorMessage;
import com.n11.userservice.exception.ItemNotFoundException;
import com.n11.userservice.mapper.ReviewMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService extends BaseEntityService<Review, ReviewRepository> {
	private final UserService userService;
	private final RestaurantClient restaurantClient;
	protected ReviewService(ReviewRepository repository, UserService userService, RestaurantClient restaurantClient) {
		super(repository);
		this.userService = userService;
		this.restaurantClient = restaurantClient;
	}

	@Override
	public Review save(Review review){
		userService.findByIdWithControl(review.getUserId());
		if (!restaurantClient.getRestaurant(review.getRestaurantId()).isSuccess()){
			throw new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND);
		}
		return super.save(review);
	}

	public ReviewDTO mapToReviewDTO(Review review){
		RestaurantDTO restaurant = restaurantClient.getRestaurant(review.getRestaurantId()).getData();
		return ReviewMapper.INSTANCE.convertToReviewDTO(review, restaurant);
	}

	public List<ReviewDTO> mapToReviewDTOs(List<Review> reviews){
		List<ReviewDTO> reviewDTOs = new ArrayList<>();
		for(Review review: reviews){
			RestaurantDTO restaurant = restaurantClient.getRestaurant(review.getRestaurantId()).getData();
			ReviewDTO reviewDTO = ReviewMapper.INSTANCE.convertToReviewDTO(review, restaurant);
			reviewDTOs.add(reviewDTO);
		}
		return reviewDTOs;
	}
}
