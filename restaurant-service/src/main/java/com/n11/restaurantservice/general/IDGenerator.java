package com.n11.restaurantservice.general;

import java.util.UUID;

public class IDGenerator {
	public static String generateID() {
		return UUID.randomUUID().toString();
	}
}
