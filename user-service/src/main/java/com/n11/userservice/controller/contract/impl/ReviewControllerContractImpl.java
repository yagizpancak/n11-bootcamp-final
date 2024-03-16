package com.n11.userservice.controller.contract.impl;

import com.n11.userservice.controller.contract.ReviewControllerContract;
import com.n11.userservice.dto.ReviewDTO;
import com.n11.userservice.entity.Review;
import com.n11.userservice.mapper.ReviewMapper;
import com.n11.userservice.request.ReviewEditCommentRequest;
import com.n11.userservice.request.ReviewEditScoreRequest;
import com.n11.userservice.request.ReviewSaveRequest;
import com.n11.userservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewControllerContractImpl implements ReviewControllerContract {
	private final ReviewService reviewService;
	@Override
	public List<ReviewDTO> getAllReviews() {
		List<Review> reviewList = reviewService.findAll();
		return reviewService.mapToReviewDTOs(reviewList);
	}

	@Override
	public ReviewDTO getReviewById(Long id) {
		Review review = reviewService.findByIdWithControl(id);

		return reviewService.mapToReviewDTO(review);
	}

	@Override
	public ReviewDTO saveReview(ReviewSaveRequest reviewSaveRequest) {
		Review review = ReviewMapper.INSTANCE.convertToReview(reviewSaveRequest);
		review = reviewService.save(review);
		return reviewService.mapToReviewDTO(review);
	}

	@Override
	public ReviewDTO updateReviewComment(Long id, ReviewEditCommentRequest request) {
		Review review = reviewService.findByIdWithControl(id);
		review.setText(request.text());
		review = reviewService.save(review);
		return reviewService.mapToReviewDTO(review);
	}

	@Override
	public ReviewDTO updateReviewScore(Long id, ReviewEditScoreRequest request) {
		Review review = reviewService.findByIdWithControl(id);
		review.setScore(request.score());
		review = reviewService.save(review);
		return reviewService.mapToReviewDTO(review);
	}

	@Override
	public void deleteReview(Long id) {
		reviewService.delete(id);
	}

}
