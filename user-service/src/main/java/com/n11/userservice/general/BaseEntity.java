package com.n11.userservice.general;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@ToString
public abstract class BaseEntity implements BaseModel{

	@Embedded
	private BaseAdditionalFields baseAdditionalFields;
}
