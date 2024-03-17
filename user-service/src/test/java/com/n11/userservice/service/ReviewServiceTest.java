package com.n11.userservice.service;

import com.n11.userservice.UserServiceApplication;
import com.n11.userservice.client.ClientResponse;
import com.n11.userservice.client.RestaurantClient;
import com.n11.userservice.dao.ReviewRepository;
import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.entity.Review;
import com.n11.userservice.exception.ItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {UserServiceApplication.class})
class ReviewServiceTest {
	private BaseEntityService reviewService;
	@Mock
	private ReviewRepository reviewRepository;
	@Mock
	private UserService userServices;
	@MockBean
	private RestaurantClient restaurantClient;

	@BeforeEach
	public void setUp() {
		reviewService = new ReviewService(reviewRepository, userServices, restaurantClient);
	}
	@Test
	void shouldDeleteReview(){
		//given
		//when
		reviewService.delete(1L);
		//then
		verify(reviewRepository, times(1)).deleteById(anyLong());
	}

	@Test
	void shouldFindAll(){
		//given
		List<Review> expected = new ArrayList<>();
		//when
		List<Review> actual = reviewService.findAll();
		//then
		verify(reviewRepository, times(1)).findAll();
		assertEquals(expected, actual);
	}

	@Test
	void shouldFindById(){
		//given
		Review expected = new Review(1L, 1L, "767718bd-4897-46fe-9460-215edcb1d93d", 5, "test");;
		when(reviewRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

		//when
		Review actual = (Review) reviewService.findByIdWithControl(expected.getId());

		//then
		verify(reviewRepository, times(1)).findById(anyLong());
		assertEquals(expected, actual);
	}

	@Test
	void shouldThrowExceptionWhenReviewNotFound(){
		//given
		when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

		//when & then
		assertThrows(ItemNotFoundException.class, () -> reviewService.findByIdWithControl(0L));
	}

	@Test
	void shouldSaveReview(){
		//given
		Review review = new Review(null, 1L, "767718bd-4897-46fe-9460-215edcb1d93d", 5, "test");

		when(userServices.findByIdWithControl(anyLong())).thenReturn(null);
		ClientResponse<RestaurantDTO> response = new ClientResponse<>();
		response.setSuccess(true);
		when(restaurantClient.getRestaurant(anyString())).thenReturn(response);

		//when
		reviewService.save(review);

		//then
		verify(reviewRepository, times(1)).save(any(Review.class));
	}

	@Test
	void shouldUpdateReview(){
		//given
		Review review = new Review(1L, 1L, "767718bd-4897-46fe-9460-215edcb1d93d", 5, "test");

		when(userServices.findByIdWithControl(anyLong())).thenReturn(null);
		ClientResponse<RestaurantDTO> response = new ClientResponse<>();
		response.setSuccess(true);
		when(restaurantClient.getRestaurant(anyString())).thenReturn(response);

		//when
		reviewService.save(review);

		//then
		verify(reviewRepository, times(1)).save(any(Review.class));
	}

	@Test
	void shouldThrowExceptionWhenUserNotFound(){
		//given
		Review review = new Review(1L, 1L, "767718bd-4897-46fe-9460-215edcb1d93d", 5, "test");

		when(userServices.findByIdWithControl(anyLong())).thenThrow(ItemNotFoundException.class);

		//when & then
		assertThrows(ItemNotFoundException.class, () -> reviewService.save(review));
	}

	@Test
	void shouldThrowExceptionWhenRestaurantNotFound(){
		//given
		Review review = new Review(1L, 1L, "767718bd-4897-46fe-9460-215edcb1d93d", 5, "test");

		when(userServices.findByIdWithControl(anyLong())).thenReturn(null);
		ClientResponse<RestaurantDTO> response = new ClientResponse<>();
		response.setSuccess(false);
		when(restaurantClient.getRestaurant(anyString())).thenReturn(response);

		//when & then
		assertThrows(ItemNotFoundException.class, () -> reviewService.save(review));
	}

}
