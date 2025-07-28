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
	private String currentNumber;
	private String minNumber;

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

	
	public String getCurrentNumber() {
		return currentNumber;
	}

	public void setCurrentNumber(String currentNumber) {
		this.currentNumber = currentNumber;
	}

	public String getMinNumber() {
		return minNumber;
	}

	public void setMinNumber(String minNumber) {
		this.minNumber = minNumber;
	}

	public String getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(String maxNumber) {
		this.maxNumber = maxNumber;
	}

	private String maxNumber;
}
