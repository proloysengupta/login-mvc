package com.proloy.demo.service;

public interface ApiCall {

	public void saveUSer(String userName, String email, String password2);

	public void changePassword(String userName, String email, String password1, String password2);
}
