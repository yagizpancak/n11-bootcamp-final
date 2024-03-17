package com.n11.userservice.service;

import com.n11.userservice.UserServiceApplication;
import com.n11.userservice.client.ClientResponse;
import com.n11.userservice.client.RestaurantClient;
import com.n11.userservice.dao.ReviewRepository;
import com.n11.userservice.dto.RestaurantDistanceDTO;
import com.n11.userservice.dto.RestaurantSuggestDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.enums.UserGender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {UserServiceApplication.class})
class SuggestionServiceTest {

	private SuggestionService suggestionService;
	@MockBean
	private RestaurantClient restaurantClient;
	@Mock
	private ReviewRepository repository;
	@BeforeEach
	public void setUp() {
		suggestionService = new SuggestionService(repository, restaurantClient);
	}

	@Test
	void shouldGetSuggestion(){
		//given
		RestaurantDistanceDTO restaurant1 = new RestaurantDistanceDTO("767718bd-4897-46fe-9460-215edcb1d93d", "Abc Restaurant1", BigDecimal.ZERO);
		RestaurantDistanceDTO restaurant2 = new RestaurantDistanceDTO("7e3b72ca-0894-48c2-97c2-a4e1241ffb2d", "Abc Restaurant2", BigDecimal.ZERO);
		RestaurantDistanceDTO restaurant3 = new RestaurantDistanceDTO("c159f1e8-ef4a-4e27-88e8-df8dedf5a5e6", "Abc Restaurant3", BigDecimal.ZERO);

		List<RestaurantDistanceDTO> restaurants = new ArrayList<>();
		restaurants.add(restaurant1);
		restaurants.add(restaurant2);
		restaurants.add(restaurant3);

		ClientResponse<List<RestaurantDistanceDTO>> response = new ClientResponse<>();
		response.setData(restaurants);
		response.setSuccess(true);
		when(restaurantClient.findByDistanceLessThan(any(BigDecimal.class), any(BigDecimal.class))).thenReturn(response);
		when(repository.findAveragePointsByRestaurantId(anyString())).thenReturn(5.0);

		User user = new User(1L,"John","Doe","johndoe","test@test.com",
				LocalDate.of(2022,10,10), UserGender.MALE,
				BigDecimal.TEN, BigDecimal.ZERO);

		List<RestaurantSuggestDTO> expected = new ArrayList<>();
		RestaurantSuggestDTO suggest1 = RestaurantSuggestDTO.builder()
				.id(restaurant1.id())
				.name(restaurant1.name())
				.distance(restaurant1.distance())
				.reviewPoint(BigDecimal.valueOf(5.0))
				.totalPoint(BigDecimal.valueOf(100.0))
				.build();
		expected.add(suggest1);

		RestaurantSuggestDTO suggest2 = RestaurantSuggestDTO.builder()
				.id(restaurant2.id())
				.name(restaurant2.name())
				.distance(restaurant2.distance())
				.reviewPoint(BigDecimal.valueOf(5.0))
				.totalPoint(BigDecimal.valueOf(100.0))
				.build();
		expected.add(suggest2);

		when(repository.findAveragePointsByRestaurantId(restaurant3.id())).thenReturn(null);
		RestaurantSuggestDTO suggest3 = RestaurantSuggestDTO.builder()
				.id(restaurant3.id())
				.name(restaurant3.name())
				.distance(restaurant3.distance())
				.reviewPoint(BigDecimal.ZERO)
				.totalPoint(BigDecimal.valueOf(30.0))
				.build();
		expected.add(suggest3);

		//when
		List<RestaurantSuggestDTO> actual = suggestionService.getSuggestion(user);

		//then
		verify(restaurantClient, times(1)).findByDistanceLessThan(any(BigDecimal.class), any(BigDecimal.class));
		assertEquals(expected.size(), actual.size());
		assertEquals(expected, actual);
	}
}
