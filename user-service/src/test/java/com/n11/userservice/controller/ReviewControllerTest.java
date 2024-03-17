package com.n11.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.n11.userservice.UserServiceApplication;
import com.n11.userservice.client.ClientResponse;
import com.n11.userservice.client.RestaurantClient;
import com.n11.userservice.dao.ReviewRepository;
import com.n11.userservice.dao.UserRepository;
import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.entity.Review;
import com.n11.userservice.entity.User;
import com.n11.userservice.enums.UserGender;
import com.n11.userservice.request.ReviewEditCommentRequest;
import com.n11.userservice.request.ReviewEditScoreRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {UserServiceApplication.class})
class ReviewControllerTest extends BaseControllerTest{
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@MockBean
	private RestaurantClient restaurantClient;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
	}


	@Test
	void shouldSaveReview() throws Exception {
		RestaurantDTO restaurant = new RestaurantDTO("767718bd-4897-46fe-9460-215edcb1d93d", "Abc Restaurant", BigDecimal.TEN, BigDecimal.ZERO);
		ClientResponse<RestaurantDTO> response = new ClientResponse<>();
		response.setData(restaurant);
		response.setSuccess(true);
		Mockito.when(restaurantClient.getRestaurant("767718bd-4897-46fe-9460-215edcb1d93d")).thenReturn(response);

		User user = new User(1L,"John","Doe","johndoe","test@test.com",
				LocalDate.of(2022,10,10), UserGender.MALE,
				BigDecimal.valueOf(10.05), BigDecimal.ZERO);
		user = userRepository.save(user);

		String requestAsString = "{\n"
				+ "  \"userId\": %s,\n"
				+ "  \"restaurantId\": \"767718bd-4897-46fe-9460-215edcb1d93d\",\n"
				+ "  \"score\": 5,\n"
				+ "  \"text\": \"test comment\"\n"
				+ "}";
		requestAsString = String.format(requestAsString, user.getId());

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/reviews")
						.content(requestAsString)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();


		boolean success = isSuccess(mvcResult);
		assertTrue(success);
	}

	@Test
	void shouldGetReviewsByUserId() throws Exception {
		RestaurantDTO restaurant = new RestaurantDTO("767718bd-4897-46fe-9460-215edcb1d93d", "Abc Restaurant", BigDecimal.TEN, BigDecimal.ZERO);
		ClientResponse<RestaurantDTO> response = new ClientResponse<>();
		response.setData(restaurant);
		response.setSuccess(true);
		Mockito.when(restaurantClient.getRestaurant("767718bd-4897-46fe-9460-215edcb1d93d")).thenReturn(response);

		User user = new User(1L,"John","Doe","johndoe","test@test.com",
				LocalDate.of(2022,10,10), UserGender.MALE,
				BigDecimal.valueOf(10.05), BigDecimal.ZERO);
		userRepository.save(user);

		Review review = new Review();
		review.setUserId(1L);
		review.setRestaurantId("767718bd-4897-46fe-9460-215edcb1d93d");
		review.setScore(5);
		review.setText("test");
		review = reviewRepository.save(review);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/v1/reviews/%s", review.getId())))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		boolean success = isSuccess(mvcResult);
		assertTrue(success);
	}

	@Test
	void shouldGetReviews() throws Exception {
		RestaurantDTO restaurant = new RestaurantDTO("767718bd-4897-46fe-9460-215edcb1d93d", "Abc Restaurant", BigDecimal.TEN, BigDecimal.ZERO);
		ClientResponse<RestaurantDTO> response = new ClientResponse<>();
		response.setData(restaurant);
		response.setSuccess(true);
		Mockito.when(restaurantClient.getRestaurant("767718bd-4897-46fe-9460-215edcb1d93d")).thenReturn(response);

		User user = new User(1L,"John","Doe","johndoe","test@test.com",
				LocalDate.of(2022,10,10), UserGender.MALE,
				BigDecimal.valueOf(10.05), BigDecimal.ZERO);
		userRepository.save(user);

		Review review1 = new Review(1L, 1L, "767718bd-4897-46fe-9460-215edcb1d93d", 5, "test");
		reviewRepository.save(review1);

		Review review2 = new Review(1L, 1L, "767718bd-4897-46fe-9460-215edcb1d93d", 5, "test2");
		reviewRepository.save(review2);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reviews"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		boolean success = isSuccess(mvcResult);
		assertTrue(success);
	}


	@Test
	void shouldEditText() throws Exception {
		RestaurantDTO restaurant = new RestaurantDTO("767718bd-4897-46fe-9460-215edcb1d93d", "Abc Restaurant", BigDecimal.TEN, BigDecimal.ZERO);
		ClientResponse<RestaurantDTO> response = new ClientResponse<>();
		response.setData(restaurant);
		response.setSuccess(true);
		Mockito.when(restaurantClient.getRestaurant("767718bd-4897-46fe-9460-215edcb1d93d")).thenReturn(response);

		User user = new User(1L,"John","Doe","johndoe","test@test.com",
				LocalDate.of(2022,10,10), UserGender.MALE,
				BigDecimal.valueOf(10.05), BigDecimal.ZERO);
		user = userRepository.save(user);

		Review review = new Review();
		review.setUserId(user.getId());
		review.setRestaurantId("767718bd-4897-46fe-9460-215edcb1d93d");
		review.setScore(5);
		review.setText("test");
		review = reviewRepository.save(review);

		ReviewEditCommentRequest request  = new ReviewEditCommentRequest("new comment");
		String requestAsString = objectMapper.writeValueAsString(request);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(String.format("/api/v1/reviews/%s/text", review.getId()))
						.content(requestAsString)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		boolean success = isSuccess(mvcResult);
		assertTrue(success);
	}

	@Test
	void shouldEditScore() throws Exception {
		RestaurantDTO restaurant = new RestaurantDTO("767718bd-4897-46fe-9460-215edcb1d93d", "Abc Restaurant", BigDecimal.TEN, BigDecimal.ZERO);
		ClientResponse<RestaurantDTO> response = new ClientResponse<>();
		response.setData(restaurant);
		response.setSuccess(true);
		Mockito.when(restaurantClient.getRestaurant("767718bd-4897-46fe-9460-215edcb1d93d")).thenReturn(response);

		User user = new User(1L,"John","Doe","johndoe","test@test.com",
				LocalDate.of(2022,10,10), UserGender.MALE,
				BigDecimal.valueOf(10.05), BigDecimal.ZERO);
		user = userRepository.save(user);

		Review review = new Review();
		review.setUserId(user.getId());
		review.setRestaurantId("767718bd-4897-46fe-9460-215edcb1d93d");
		review.setScore(5);
		review.setText("test");
		review = reviewRepository.save(review);

		ReviewEditScoreRequest request  = new ReviewEditScoreRequest(1);
		String requestAsString = objectMapper.writeValueAsString(request);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(String.format("/api/v1/reviews/%s/score", review.getId()))
						.content(requestAsString)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		boolean success = isSuccess(mvcResult);
		assertTrue(success);
	}

	@Test
	void shouldDeleteReview() throws Exception {
		RestaurantDTO restaurant = new RestaurantDTO("767718bd-4897-46fe-9460-215edcb1d93d", "Abc Restaurant", BigDecimal.TEN, BigDecimal.ZERO);
		ClientResponse<RestaurantDTO> response = new ClientResponse<>();
		response.setData(restaurant);
		response.setSuccess(true);
		Mockito.when(restaurantClient.getRestaurant("767718bd-4897-46fe-9460-215edcb1d93d")).thenReturn(response);

		User user = new User(1L,"John","Doe","johndoe","test@test.com",
				LocalDate.of(2022,10,10), UserGender.MALE,
				BigDecimal.valueOf(10.05), BigDecimal.ZERO);
		userRepository.save(user);

		Review review = new Review(1L, 1L, "767718bd-4897-46fe-9460-215edcb1d93d", 5, "test");
		reviewRepository.save(review);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/reviews/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		boolean success = isSuccess(mvcResult);
		assertTrue(success);
	}
}
