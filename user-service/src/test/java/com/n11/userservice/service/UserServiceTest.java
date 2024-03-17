package com.n11.userservice.service;

import com.n11.userservice.UserServiceApplication;
import com.n11.userservice.dao.UserRepository;
import com.n11.userservice.entity.User;
import com.n11.userservice.enums.UserGender;
import com.n11.userservice.exception.ItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {UserServiceApplication.class})
class UserServiceTest {
	private BaseEntityService userService;
	@Mock
	private UserRepository userRepository;

	@BeforeEach
	public void setUp() {
		userService = new UserService(userRepository);
	}
	@Test
	void shouldDeleteUser(){
		//given
		//when
		userService.delete(1L);
		//then
		verify(userRepository, times(1)).deleteById(anyLong());
	}

	@Test
	void shouldFindAll(){
		//given
		List<User> expected = new ArrayList<>();
		//when
		List<User> actual = userService.findAll();
		//then
		verify(userRepository, times(1)).findAll();
		assertEquals(expected, actual);
	}

	@Test
	void shouldFindById(){
		//given
		User expected = new User(1L,"John","Doe","johndoe","test@test.com",
				LocalDate.of(2022,10,10), UserGender.MALE,
				BigDecimal.valueOf(10.05), BigDecimal.ZERO);
		when(userRepository.findById(expected.getId())).thenReturn(Optional.of(expected));

		//when
		User actual = (User) userService.findByIdWithControl(expected.getId());

		//then
		verify(userRepository, times(1)).findById(anyLong());
		assertEquals(expected, actual);
	}

	@Test
	void shouldThrowExceptionWhenUserNotFound(){
		//given
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		//when & then
		assertThrows(ItemNotFoundException.class, () -> userService.findByIdWithControl(0L));
	}

	@Test
	void shouldSaveUser(){
		//given
		User user = new User(null,"John","Doe","johndoe","test@test.com",
				LocalDate.of(2022,10,10), UserGender.MALE,
				BigDecimal.valueOf(10.05), BigDecimal.ZERO);

		//when
		userService.save(user);

		//then
		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	void shouldUpdateUser(){
		//given
		User user = new User(1L,"John","Doe","johndoe","test@test.com",
				LocalDate.of(2022,10,10), UserGender.MALE,
				BigDecimal.valueOf(10.05), BigDecimal.ZERO);

		//when
		userService.save(user);

		//then
		verify(userRepository, times(1)).save(any(User.class));
	}
}
