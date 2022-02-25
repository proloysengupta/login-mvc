package com.proloy.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "register-mvc", url = "http://localhost:8081/")
public interface LoginMvcProxy {

	@RequestMapping("/register-user/{userName}/{email}/{password}")
	public String registerUser(@PathVariable("userName") String userName, @PathVariable("email") String email,
			@PathVariable("password") String password);

	@RequestMapping("/changepassword-user/{userName}/{email}/{password1}/{password2}")
	public String changePassword(@PathVariable("userName") String userName, @PathVariable("email") String email,
			@PathVariable("password1") String password1, @PathVariable("password2") String password2);

	@RequestMapping("/changeDetails-user/{userName1}/{userName}/{email}/{password1}/{password2}")
	public String changeDetails(@PathVariable("userName1") String userName1, @PathVariable("userName") String userName,
			@PathVariable("email") String email, @PathVariable("password1") String password1,
			@PathVariable("password2") String password2);
}
