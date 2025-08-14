package com.orionsolwings.osbiz.businessConfig.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "number_range")
public class NumberRange {

	@Id
	private String id;

	@Indexed(unique = true)
	private String moduleType;

	private String businessCode;
	private Long currentNumber;
	private Long minNumber;

	public String getId() {
		return id;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	
	public Long getCurrentNumber() {
		return currentNumber;
	}

	public void setCurrentNumber(Long currentNumber) {
		this.currentNumber = currentNumber;
	}

	public Long getMinNumber() {
		return minNumber;
	}

	public void setMinNumber(Long minNumber) {
		this.minNumber = minNumber;
	}

	public Long getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(Long maxNumber) {
		this.maxNumber = maxNumber;
	}

	private Long maxNumber;
}
