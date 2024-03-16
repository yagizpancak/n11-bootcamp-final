package com.n11.userservice.controller.contract;

import com.n11.userservice.dto.ReviewDTO;
import com.n11.userservice.request.ReviewEditCommentRequest;
import com.n11.userservice.request.ReviewEditScoreRequest;
import com.n11.userservice.request.ReviewSaveRequest;

import java.util.List;

public interface ReviewControllerContract {
	List<ReviewDTO> getAllReviews();
	ReviewDTO getReviewById(Long id);
	ReviewDTO saveReview(ReviewSaveRequest reviewSaveRequest);
	ReviewDTO updateReviewComment(Long id, ReviewEditCommentRequest request);
	ReviewDTO updateReviewScore(Long id, ReviewEditScoreRequest request);
	void deleteReview(Long id);

}
