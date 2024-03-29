package com.n11.userservice.exception;

import com.n11.userservice.general.BaseErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends BusinessException {

	public ItemNotFoundException(BaseErrorMessage baseErrorMessage) {
		super(baseErrorMessage);
	}
}
