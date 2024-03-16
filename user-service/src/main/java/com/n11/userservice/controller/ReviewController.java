package com.n11.userservice.controller;

import com.n11.userservice.controller.contract.ReviewControllerContract;
import com.n11.userservice.dto.ReviewDTO;
import com.n11.userservice.general.RestResponse;
import com.n11.userservice.request.ReviewEditCommentRequest;
import com.n11.userservice.request.ReviewEditScoreRequest;
import com.n11.userservice.request.ReviewSaveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
	private final ReviewControllerContract reviewControllerContract;

	public ReviewController(ReviewControllerContract reviewControllerContract){
		this.reviewControllerContract = reviewControllerContract;
	}

	@GetMapping
	public ResponseEntity<RestResponse<List<ReviewDTO>>> findAll(){
		List<ReviewDTO> reviews = reviewControllerContract.getAllReviews();
		return ResponseEntity.ok(RestResponse.of(reviews));
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<ReviewDTO>> findById(@PathVariable Long id){
		ReviewDTO review = reviewControllerContract.getReviewById(id);
		return ResponseEntity.ok(RestResponse.of(review));
	}

	@PostMapping
	public ResponseEntity<RestResponse<ReviewDTO>> save(@RequestBody ReviewSaveRequest reviewSaveRequest){
		ReviewDTO review = reviewControllerContract.saveReview(reviewSaveRequest);
		return ResponseEntity.ok(RestResponse.of(review));
	}

	@PatchMapping("/{id}/text")
	public ResponseEntity<RestResponse<ReviewDTO>> editComment(@PathVariable Long id, @RequestBody ReviewEditCommentRequest request) {
		ReviewDTO review = reviewControllerContract.updateReviewComment(id, request);
		return ResponseEntity.ok(RestResponse.of(review));
	}

	@PatchMapping("/{id}/score")
	public ResponseEntity<RestResponse<ReviewDTO>> updateScore(@PathVariable Long id, @RequestBody ReviewEditScoreRequest request) {
		ReviewDTO review = reviewControllerContract.updateReviewScore(id, request);
		return ResponseEntity.ok(RestResponse.of(review));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Object>> delete(@PathVariable Long id){
		reviewControllerContract.deleteReview(id);
		return ResponseEntity.ok(RestResponse.empty());
	}
}
