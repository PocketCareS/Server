package com.PocketCare.pocketCare.model;

public class BaseResponse {
	private String response;
	private int responseCode;
	
	public BaseResponse(){
		
	}
	
	public BaseResponse(String response, int responseCode) {
		super();
		this.response = response;
		this.responseCode = responseCode;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
}
