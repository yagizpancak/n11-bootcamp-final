package com.n11.userservice.controller;

import com.n11.userservice.controller.contract.UserControllerContract;
import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.general.RestResponse;
import com.n11.userservice.request.UserSaveRequest;
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



	@PostMapping
	public ResponseEntity<RestResponse<UserDTO>> save(@RequestBody UserSaveRequest userSaveRequest){
		UserDTO user = userControllerContract.saveUser(userSaveRequest);
		return ResponseEntity.ok(RestResponse.of(user));
	}
}
