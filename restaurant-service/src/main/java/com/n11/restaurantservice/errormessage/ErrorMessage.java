package com.n11.restaurantservice.errormessage;

import com.n11.restaurantservice.general.BaseErrorMessage;
import lombok.Getter;

@Getter
public enum ErrorMessage implements BaseErrorMessage {
	ITEM_NOT_FOUND("Item not found!"),
	ACTIVE_PRODUCT_NOT_FOUND("Active product not found!");

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
