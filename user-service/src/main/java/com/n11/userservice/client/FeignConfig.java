package com.n11.userservice.client;

import com.n11.userservice.errormessage.ErrorMessage;
import com.n11.userservice.exception.ItemNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
	@Bean
	public ErrorDecoder errorDecoder() {
		return new FeignErrorDecoder();
	}
}

class FeignErrorDecoder implements ErrorDecoder {
	private final ErrorDecoder errorDecoder = new Default();
	@Override
	public Exception decode(String methodKey, Response response) {
		if (response.status() == 404) {
			return new ItemNotFoundException(ErrorMessage.RESTAURANT_NOT_FOUND);
		}
		return errorDecoder.decode(methodKey, response);
	}
}