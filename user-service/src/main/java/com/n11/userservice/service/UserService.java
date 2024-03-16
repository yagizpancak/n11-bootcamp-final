package com.n11.userservice.service;

import com.n11.userservice.dao.UserRepository;
import com.n11.userservice.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseEntityService<User, UserRepository> {
	protected UserService(UserRepository repository) {
		super(repository);
	}
}
