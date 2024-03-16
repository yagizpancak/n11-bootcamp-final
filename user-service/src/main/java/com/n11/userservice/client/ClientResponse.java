package com.n11.userservice.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ClientResponse<T> {
	private T data;
	private LocalDateTime responseDate;
	private boolean isSuccess;
	private String messages;
}
