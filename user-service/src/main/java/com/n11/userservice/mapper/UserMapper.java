package com.n11.userservice.mapper;

import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.request.UserSaveRequest;
import com.n11.userservice.request.UserUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	User convertToUser(UserSaveRequest request);
	UserDTO convertToUserDTO(User user);
	List<UserDTO> convertToUserDTOs(List<User> users);

	@Mapping(target = "id", ignore = true)
	void updateUserFields(@MappingTarget User user, UserUpdateRequest request);
}
