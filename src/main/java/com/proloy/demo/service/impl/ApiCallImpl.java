package com.proloy.demo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiCallImpl {

	RestTemplate restTemplate = new RestTemplate();

	private static final String microServiceSaveUserUrl = "http://localhost:8081/register-user/";
	private static final String microServiceChangePassUrl = "http://localhost:8081/changepassword-user/";

	public void saveUSer(String userName, String email, String password2) {
		restTemplate.getForObject(microServiceSaveUserUrl + userName + "/" + email + "/" + password2, String.class);
	}

	public void changePassword(String userName, String email, String password1, String password2) {
		restTemplate.getForObject(
				microServiceChangePassUrl + userName + "/" + email + "/" + password1 + "/" + password2, String.class);
	}

}
