package com.n11.userservice.controller.contract.impl;

import com.n11.userservice.controller.contract.UserControllerContract;
import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.mapper.UserMapper;
import com.n11.userservice.request.UserSaveRequest;
import com.n11.userservice.request.UserUpdateRequest;
import com.n11.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> userList = userService.findAll();
		return UserMapper.INSTANCE.convertToUserDTOs(userList);
	}

	@Override
	public UserDTO getUserById(Long id) {
		User user = userService.findByIdWithControl(id);
		return UserMapper.INSTANCE.convertToUserDTO(user);
	}

	@Override
	public UserDTO updateUser(UserUpdateRequest request) {
		User user = userService.findByIdWithControl(request.id());
		UserMapper.INSTANCE.updateUserFields(user, request);
		user = userService.save(user);
		return UserMapper.INSTANCE.convertToUserDTO(user);
	}

	@Override
	public void deleteUser(Long id) {
		userService.delete(id);
	}
}
