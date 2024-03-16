package com.n11.userservice.mapper;

import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.request.UserSaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	User convertToUser(UserSaveRequest request);
	UserDTO convertToUserDTO(User user);
}
