package com.n11.userservice.controller.contract;

import com.n11.userservice.dto.RestaurantSuggestDTO;
import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.request.UserSaveRequest;
import com.n11.userservice.request.UserUpdateRequest;

import java.util.List;

public interface UserControllerContract {

	UserDTO saveUser(UserSaveRequest userSaveRequest);
	List<UserDTO> getAllUsers();
	UserDTO getUserById(Long id);
	UserDTO updateUser(UserUpdateRequest request);
	void deleteUser(Long id);
	List<RestaurantSuggestDTO> getRestaurantSuggestion(Long id);
}
