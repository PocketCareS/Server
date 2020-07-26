package com.PocketCare.pocketCare.Exception;

public class ClientException extends CustomException {
	Integer status;
	String message;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ClientException(){
	        this.status = 400;
	        this.message = "Bad Request";
	    }

}
