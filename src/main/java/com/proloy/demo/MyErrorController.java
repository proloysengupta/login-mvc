package com.proloy.demo;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyErrorController implements ErrorController {

	public String getErrorPath() {
		return "/error";
	}

	@GetMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
		String errorPage = "error";

		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				errorPage = "Error 404 NOT FOUND";

			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				errorPage = "Error 403 FORBIDDEN";

			} else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
				errorPage = "Error 400 BAD REQUEST";

			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				errorPage = "Error 500 INTERNAL SERVER ERROR";

			}
		}

		model.addAttribute("errorPage", errorPage);
		return "error";
	}

}
