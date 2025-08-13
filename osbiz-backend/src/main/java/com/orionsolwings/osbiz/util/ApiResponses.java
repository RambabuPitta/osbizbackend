package com.orionsolwings.osbiz.util;

import lombok.AllArgsConstructor;
import lombok.Data;

public class ApiResponses<T> {

    private String message;
    private String status;
    private String errorCode; // Added for error handling
    private T data;

    public ApiResponses() {
    }
    
    
    @Data
    @AllArgsConstructor
    public class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;
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

    public ApiResponses(String message, String status, String errorCode, T data) {
        this.message = message;
        this.status = status;
        this.errorCode = errorCode;
        this.data = data;
    }

    // Getters & Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
