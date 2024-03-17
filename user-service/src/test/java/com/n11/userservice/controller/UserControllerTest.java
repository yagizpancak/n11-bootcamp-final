package com.n11.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.n11.userservice.UserServiceApplication;
import com.n11.userservice.client.ClientResponse;
import com.n11.userservice.client.RestaurantClient;
import com.n11.userservice.dao.UserRepository;
import com.n11.userservice.dto.RestaurantDistanceDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.enums.UserGender;
import com.n11.userservice.general.KafkaProducerService;
import com.n11.userservice.request.UserUpdateRequest;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {UserServiceApplication.class})
class UserControllerTest extends BaseControllerTest{
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@MockBean
	private RestaurantClient restaurantClient;

	@Autowired
	private UserRepository userRepository;

	@MockBean
	private KafkaProducerService kafka;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
	}

	@Test
	void shouldSaveUser() throws Exception {
		String requestAsString = "{\n"
				+ "  \"userId\": 1,\n"
				+ "  \"name\": \"John\",\n"
				+ "  \"surname\": \"Doe\",\n"
				+ "  \"username\": \"johndoe\",\n"
				+ "  \"email\": \"test@test.com\",\n"
				+ "  \"birthDate\": \"2022-03-17\",\n"
				+ "  \"gender\": \"MALE\",\n"
				+ "  \"latitude\": 10.05,\n"
				+ "  \"longitude\": 0\n"
				+ "}";

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
						.content(requestAsString)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();


		boolean success = isSuccess(mvcResult);
		assertTrue(success);
	}

	@Test
	void shouldGetUser() throws Exception {
		User user = new User();
		user.setName("John");
		user.setSurname("Doe");
		user.setUsername("johndoe");
		user.setEmail("test@test.com");
		user.setBirthDate(LocalDate.of(2022,10,10));
		user.setGender(UserGender.MALE);
		user.setLatitude(BigDecimal.valueOf(10.05));
		user.setLongitude(BigDecimal.ZERO);
		user = userRepository.save(user);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/v1/users/%s", user.getId())))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		boolean success = isSuccess(mvcResult);
		assertTrue(success);
	}

	@Test
	void shouldGetUsers() throws Exception {
		User user1 = new User(1L,"John","Doe","johndoe","test@test.com",
				LocalDate.of(2022,10,10), UserGender.MALE,
				BigDecimal.valueOf(10.05), BigDecimal.ZERO);
		userRepository.save(user1);

		User user2 = new User(2L,"John","Doe","johndoe","test@test.com",
				LocalDate.of(2022,10,10), UserGender.MALE,
				BigDecimal.valueOf(10.05), BigDecimal.ZERO);
		userRepository.save(user2);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		boolean success = isSuccess(mvcResult);
		assertTrue(success);
	}

	@Test
	void shouldDeleteUser() throws Exception {
		User user = new User(1L,"John","Doe","johndoe","test@test.com",
				LocalDate.of(2022,10,10), UserGender.MALE,
				BigDecimal.valueOf(10.05), BigDecimal.ZERO);
		userRepository.save(user);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		boolean success = isSuccess(mvcResult);
		assertTrue(success);
	}

	@Test
	void shouldUpdateUser() throws Exception {
		User user = new User(1L,"John","Doe","johndoe","test@test.com",
				LocalDate.of(2022,10,10), UserGender.MALE,
				BigDecimal.valueOf(10.05), BigDecimal.ZERO);
		user = userRepository.save(user);

		UserUpdateRequest request  = new UserUpdateRequest(user.getId(), "James", "Foe", "jamesfoe",
				"james@g.com", LocalDate.of(1985,10,10), UserGender.FEMALE,
				BigDecimal.valueOf(10.005), BigDecimal.valueOf(0.001));
		String requestAsString = objectMapper.writeValueAsString(request);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users")
						.content(requestAsString)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		boolean success = isSuccess(mvcResult);
		assertTrue(success);
	}

	@Test
	void shouldGetSuggestion() throws Exception {
		RestaurantDistanceDTO restaurant1 = new RestaurantDistanceDTO("767718bd-4897-46fe-9460-215edcb1d93d", "Abc Restaurant1", BigDecimal.TEN);
		RestaurantDistanceDTO restaurant2 = new RestaurantDistanceDTO("7e3b72ca-0894-48c2-97c2-a4e1241ffb2d", "Abc Restaurant2", BigDecimal.ZERO);
		RestaurantDistanceDTO restaurant3 = new RestaurantDistanceDTO("c159f1e8-ef4a-4e27-88e8-df8dedf5a5e6", "Abc Restaurant3", BigDecimal.valueOf(5.005));

		List<RestaurantDistanceDTO> restaurants = new ArrayList<>();
		restaurants.add(restaurant1);
		restaurants.add(restaurant2);
		restaurants.add(restaurant3);

		ClientResponse<List<RestaurantDistanceDTO>> response = new ClientResponse<>();
		response.setData(restaurants);
		response.setSuccess(true);
		Mockito.when(restaurantClient.findByDistanceLessThan(any(BigDecimal.class), any(BigDecimal.class))).thenReturn(response);

		User user = new User(1L,"John","Doe","johndoe","test@test.com",
				LocalDate.of(2022,10,10), UserGender.MALE,
				BigDecimal.TEN, BigDecimal.ZERO);
		user = userRepository.save(user);


		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/v1/users/%s/suggest-restaurant", user.getId())))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		boolean success = isSuccess(mvcResult);
		assertTrue(success);
	}
}
