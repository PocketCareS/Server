package com.PocketCare.pocketCare.model;

public class UserRegistrationResponse extends BaseResponse{
    public UserRegistrationResponse(String response, int responseCode) {
		super(response, responseCode);
		// TODO Auto-generated constructor stub
	}
    
    public UserRegistrationResponse() {
    
    }

    private String token;
    
    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
