package com.n11.userservice.controller;

import com.n11.userservice.controller.contract.UserControllerContract;
import com.n11.userservice.dto.RestaurantSuggestDTO;
import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.general.RestResponse;
import com.n11.userservice.request.UserSaveRequest;
import com.n11.userservice.request.UserUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	private final UserControllerContract userControllerContract;

	public UserController(UserControllerContract userControllerContract) {
		this.userControllerContract = userControllerContract;
	}

	@GetMapping("/{id}/suggest-restaurant")
	public ResponseEntity<RestResponse<List<RestaurantSuggestDTO>>> getRestaurantSuggestion(@PathVariable Long id){
		List<RestaurantSuggestDTO> restaurants = userControllerContract.getRestaurantSuggestion(id);
		return ResponseEntity.ok(RestResponse.of(restaurants));
	}

	@GetMapping
	public ResponseEntity<RestResponse<List<UserDTO>>> findAll(){
		List<UserDTO> users = userControllerContract.getAllUsers();
		return ResponseEntity.ok(RestResponse.of(users));
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<UserDTO>> findById(@PathVariable Long id){
		UserDTO user = userControllerContract.getUserById(id);
		return ResponseEntity.ok(RestResponse.of(user));
	}

	@PostMapping
	public ResponseEntity<RestResponse<UserDTO>> save(@RequestBody UserSaveRequest userSaveRequest){
		UserDTO user = userControllerContract.saveUser(userSaveRequest);
		return ResponseEntity.ok(RestResponse.of(user));
	}

	@PutMapping
	public ResponseEntity<RestResponse<UserDTO>> update(@RequestBody UserUpdateRequest userUpdateRequest){
		UserDTO user = userControllerContract.updateUser(userUpdateRequest);
		return ResponseEntity.ok(RestResponse.of(user));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<Object>> delete(@PathVariable Long id){
		userControllerContract.deleteUser(id);
		return ResponseEntity.ok(RestResponse.empty());
	}
}
