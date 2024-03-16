package com.n11.userservice.mapper;

import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.dto.ReviewDTO;
import com.n11.userservice.entity.Review;
import com.n11.userservice.request.ReviewSaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {
	ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

	Review convertToReview(ReviewSaveRequest request);
	@Mapping(target = "id", source = "review.id")
	@Mapping(target = "restaurantName", source = "restaurant.name")
	ReviewDTO convertToReviewDTO(Review review, RestaurantDTO restaurant);

}
