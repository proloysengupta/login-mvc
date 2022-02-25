package com.proloy.demo;

import org.springframework.stereotype.Component;

@Component
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public CustomException(String errorMessage) {
		super(errorMessage);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CustomException() {
		super();
	}

}
