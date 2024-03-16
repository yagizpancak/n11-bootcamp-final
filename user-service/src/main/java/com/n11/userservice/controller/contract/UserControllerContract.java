package com.n11.userservice.controller.contract;

import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.request.UserSaveRequest;

public interface UserControllerContract {
	UserDTO saveUser(UserSaveRequest userSaveRequest);
}
