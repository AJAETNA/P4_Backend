package com.practice.p4.pojo;

public class GenericResponse {
	
	private String message;

	public GenericResponse() {
		super();
	}


	public GenericResponse(String message) {
		super();
		this.message = message;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
}
