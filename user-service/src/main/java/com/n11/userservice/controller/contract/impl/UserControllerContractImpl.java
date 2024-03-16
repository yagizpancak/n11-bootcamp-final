package com.n11.userservice.controller.contract.impl;

import com.n11.userservice.controller.contract.UserControllerContract;
import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.mapper.UserMapper;
import com.n11.userservice.request.UserSaveRequest;
import com.n11.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserControllerContractImpl implements UserControllerContract {
	private final UserService userService;
	@Override
	public UserDTO saveUser(UserSaveRequest userSaveRequest) {
		User user = UserMapper.INSTANCE.convertToUser(userSaveRequest);
		user = userService.save(user);
		return UserMapper.INSTANCE.convertToUserDTO(user);
	}
}
