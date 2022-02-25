package com.proloy.demo;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.proloy.demo.feign.LoginMvcProxy;
import com.proloy.demo.model.User;
import com.proloy.demo.model.UserRepo;
import com.proloy.demo.service.impl.ApiCallImpl;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@EnableHystrix
public class LoginController {

	@Autowired
	private LoginMvcProxy proxy;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ApiCallImpl apiCall;

	RestTemplate restTemplate = new RestTemplate();

	@RequestMapping("/")
	public String checkMVC() {
		return "login";
	}

	@RequestMapping("/password")
	public String goToPassword() {
		return "changepass";
	}

	@RequestMapping("/register")
	public String goToReistrationPage() {
		return "register";
	}

	@RequestMapping("/login")
	@HystrixCommand(fallbackMethod = "fallback_login")
	public String login(@RequestParam("userName") String userName, @RequestParam("password") String password,
			Model model, HttpSession session) {
		User u = null;
		try {
			u = userRepo.findByName(userName);
		} catch (Exception e) {
			throw new CustomException("User Not found");
		}
		if (userName == "" || password == "") {
			model.addAttribute("blank", "Please enter all values!!!");
			return "login";
		}

		if (u != null) {
			Decoder decoder = Base64.getDecoder();
			byte[] bytes = decoder.decode(u.getPassword());
			String newPass = new String(bytes);

			if (newPass.equals(password)) {
				session.setAttribute("userName", userName);
				session.setAttribute("email", u.getEmail());
				session.setAttribute("password", newPass);
				return "homePage";
			}
		}
		model.addAttribute("error", "User Not Found, Kindly register !!!");
		return "login";
	}

	@RequestMapping("/set-user")
	@HystrixCommand(fallbackMethod = "fallback_save")
	public String saveUser(@RequestParam("userName") String userName, @RequestParam("email") String email,
			@RequestParam("password1") String password1, @RequestParam("password2") String password2, Model model) {
		System.out.println("=====going to register microservice from login microservice start");
		User u1 = null;
		try {
			u1 = userRepo.findByName(userName);
		} catch (Exception e) {
			throw new CustomException("User Not found");
		}
		if (userName.isEmpty() || userName == "" || email.isEmpty() || email == "" || password1.isEmpty()
				|| password1 == "" || password2 == "" || password2.isEmpty()) {
			model.addAttribute("blank", "Please enter all values!!!");
			return "register";
		}
		if (u1 == null) {
			if (password1.equals(password2)) {
				String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=\\S+$).{8,20}$";
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(password1);
				if (!m.matches()) {
					model.addAttribute("blank", "Please enter correct Password combination!!!");
					return "register";
				} else {
					Encoder encoder = Base64.getEncoder();
					String originalString = password1;
					String encodedString = encoder.encodeToString(originalString.getBytes());

					User u = new User();
					u.setName(userName);
					u.setEmail(email);
					u.setPassword(encodedString);
					userRepo.save(u);
					apiCall.saveUSer(userName, email, password2);
					// proxy.registerUser(userName, email, password2);
					model.addAttribute("registerSuccess", "Successfully registered, kindly login to continue!!!");
				}
			} else {
				model.addAttribute("registrationError", "Password Not Same !!!");
				return "register";
			}
		} else {
			model.addAttribute("registrationError", "Username is already registered !!!");
			return "register";
		}
		System.out.println("=====register microservice from login microservice end");

		return "login";
	}

	@RequestMapping("/set-pass")
	@HystrixCommand(fallbackMethod = "fallback_pass")
	public String setPassword(@RequestParam("userName") String userName, @RequestParam("email") String email,
			@RequestParam("password1") String password1, @RequestParam("password2") String password2, Model model) {
		User u = null;
		try {
			u = userRepo.findByName(userName);
		} catch (Exception e) {
			throw new CustomException("User Not found");
		}
		if (userName.isEmpty() || userName == "" || email.isEmpty() || email.isEmpty() || password1.isEmpty()
				|| password1 == "" || password2 == "" || password2.isEmpty()) {
			model.addAttribute("blank", "Please enter all values!!!");
			return "changepass";
		}
		if (u != null) {
			Decoder decoder = Base64.getDecoder();
			byte[] bytes = decoder.decode(u.getPassword());
			String newPass = new String(bytes);

			if (newPass.equals(password1)) {
				String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=\\S+$).{8,20}$";
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(password2);
				if (!m.matches()) {
					model.addAttribute("blank", "Please enter correct Password combination!!!");
					return "changepass";
				} else {
					Encoder encoder = Base64.getEncoder();
					String originalString = password2;
					String encodedString = encoder.encodeToString(originalString.getBytes());
					u.setPassword(encodedString);
					userRepo.save(u);
					apiCall.changePassword(userName, email, password1, password2);
					// proxy.changePassword(userName, email, password1,
					// password2);
					model.addAttribute("registerSuccess", "Password Changed, kindly login to continue!!!");
					return "login";
				}
			}
		}
		model.addAttribute("error", "User Not Found or Password entry is wrong, Kindly register !!!");
		return "changepass";
	}

	@RequestMapping("/set-details")
	@HystrixCommand(fallbackMethod = "fallback_update")
	public String updateDetails(@RequestParam("userName1") String userName1, @RequestParam("userName") String userName,
			@RequestParam("email") String email, @RequestParam("password1") String password1,
			@RequestParam("password2") String password2, Model model) {
		User u = null;
		User u1 = null;
		try {
			u = userRepo.findByName(userName1);
		} catch (Exception e) {
			throw new CustomException("User Not found");
		}
		if (userName.isEmpty() || userName == "" || email.isEmpty() || email == "" || password1.isEmpty()
				|| password1 == "" || password2 == "" || password2.isEmpty()) {
			model.addAttribute("blank", "Please enter all values!!!");
			return "homePage";
		}
		if (u != null) {
			u1 = userRepo.findByName(userName);
			if (u1 != null && !userName.equals(userName1)) {
				model.addAttribute("error", "User Already exists, Kindly register !!!");
				return "login";
			} else {
				u.setName(userName);
				u.setEmail(email);
				userRepo.save(u);
				proxy.changeDetails(userName1, userName, email, password1, password2);
				model.addAttribute("registerSuccess", "Successfully updated, kindly login to continue!!!");
				return "login";
			}
		}
		model.addAttribute("error", "User Not Found, Kindly register !!!");
		return "login";
	}

	private String fallback_login(String userName, String password, Model model, HttpSession session) {
		return "errorHystrix";
	}

	private String fallback_save(String userName, String email, String password1, String password2, Model model) {
		return "errorHystrix";
	}

	private String fallback_pass(String userName, String email, String password1, String password2, Model model) {
		return "errorHystrix";
	}

	private String fallback_update(String userName1, String userName, String email, String password1, String password2,
			Model model) {
		return "errorHystrix";
	}

}
