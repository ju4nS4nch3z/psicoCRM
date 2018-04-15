package com.psicocrm.controller;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.psicocrm.model.Administrator;
import com.psicocrm.model.User;
import com.psicocrm.service.UserService;
import com.psicocrm.service.EmailService.TypeEmail;

@Controller
public class LoginController implements ErrorController {

	private static final String ERROR = "/error";

	@Autowired
	private UserService userService;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping("/")
	public String home(Map<String, Object> model) {
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(Locale locale, ModelAndView mav, String error) {
		mav.setViewName("login");
		if (error != null) {
			mav.addObject("error", messageSource.getMessage("error.login", null, locale));
		}
		return mav;
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public String resetPassword(Model model) {
		model.addAttribute("userForm", new User());
		return "resetPassword";
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ModelAndView resetPassword(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
			HttpServletRequest request) {

		ModelAndView mav;
		Locale locale = request.getLocale();

		boolean success = userService.resetPassword(userForm, getUrl(request), locale);

		if (!success) {
			mav = new ModelAndView("resetPassword");
			mav.addObject("error", messageSource.getMessage("error.resetPassword", null, locale));
		} else {
			mav = new ModelAndView("login");
			mav.addObject("success", messageSource.getMessage("success.resetPassword", null, locale));
		}

		return mav;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userForm", new Administrator());
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") Administrator userForm, BindingResult bindingResult,
			Model model) {
		/*
		 * userValidator.validate(userForm, bindingResult)
		 * 
		 * if (bindingResult.hasErrors()) { return "registration"; }
		 */
		userService.save(userForm);
		// securityService.autologin(userForm.getUsername(),
		// userForm.getPasswordConfirm());

		return "login";
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(Model model) {

		User user = userService.getPrincipal();

		model.addAttribute("user", user);

		return "dashboard";
	}

	@RequestMapping(value = { "/logout" }, method = RequestMethod.POST)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		SecurityContextHolder.clearContext();
		session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		return "logout";
	}

	@Override
	public String getErrorPath() {
		return ERROR;
	}

	private String getUrl(HttpServletRequest request) {

		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}

}