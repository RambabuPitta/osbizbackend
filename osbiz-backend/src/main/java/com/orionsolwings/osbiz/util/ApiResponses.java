package com.orionsolwings.osbiz.util;

public class ApiResponses<T> {

    private String message;
    private String status;
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private T data;

    public ApiResponses() {
    }

    public ApiResponses(String message, T data) {
        this.message = message;
        this.data = data;
    }
    
    public ApiResponses(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public ApiResponses(String message, String status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
