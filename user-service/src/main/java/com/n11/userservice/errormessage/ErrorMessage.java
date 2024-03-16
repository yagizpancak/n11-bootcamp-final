package com.n11.userservice.errormessage;

import com.n11.userservice.general.BaseErrorMessage;
import lombok.Getter;

@Getter
public enum ErrorMessage implements BaseErrorMessage {
	ITEM_NOT_FOUND("Item not found!");

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
