package com.psicocrm.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psicocrm.model.Administrator;
import com.psicocrm.model.Group;
import com.psicocrm.model.Teacher;
import com.psicocrm.model.User;
import com.psicocrm.service.EmailService;
import com.psicocrm.service.GroupService;
import com.psicocrm.service.UserService;
import com.psicocrm.service.EmailService.TypeEmail;

@Controller
@SessionAttributes("groupList")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/teachers", method = RequestMethod.GET)
	public String index() {
		return "redirect:/teachers/list/1";
	}

	@RequestMapping(value = "/teachers/list/{pageNum}", method = RequestMethod.GET)
	public ModelAndView index(@PathVariable int pageNum) {
		ModelAndView model = new ModelAndView("teachers_list");
		User user = userService.getPrincipal();
		model.addObject("user", user);

		Page<Teacher> page = userService.getTeachers(pageNum, user.getId());

		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, page.getTotalPages());

		model.addObject("page", page);
		model.addObject("beginIndex", begin);
		model.addObject("endIndex", end);
		model.addObject("currentIndex", current);

		model.addObject("path", "/teachers/list/");

		return model;
	}

	@RequestMapping(value = "/teachers/save", method = RequestMethod.POST)
	public String userRegister(@ModelAttribute("teacherForm") Teacher teacher, HttpServletRequest request) {

		if (teacher != null) {
			String pass = "";
			if (teacher.getId() == null) {
				teacher.setAdministrator((Administrator) userService.getPrincipal());
				pass = userService.generatePassword();
				teacher.setPassword(pass);

				userService.save(teacher);

				emailService.sendPassword(TypeEmail.NEW_USER, teacher, pass, getUrl(request),
						request.getLocale());

			} else {
				userService.update(teacher);
			}

		}

		return "redirect:/teachers/list/1";
	}

	@RequestMapping(value = "/teachers/delete/{id}", method = RequestMethod.GET)
	public String deleteUsers(@PathVariable long id) {

		userService.deleteUser(id);

		return "redirect:/teachers/list/1";
	}

	@RequestMapping(value = { "/teachers/edit/{id}", "/edit" }, method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") long id) {
		ModelAndView model = new ModelAndView("teachers_form");

		User teacher = null;
		if (id > 0) {
			teacher = userService.getUserById(id);
		} else {
			teacher = new Teacher();
		}

		model.addObject("teacherForm", teacher);
		try {
			model.addObject("teacherGroups", new ObjectMapper().writeValueAsString(((Teacher) teacher).getGroups()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		model.addObject("user", userService.getPrincipal());

		return model;
	}

	@ModelAttribute("groupList")
	public List<Group> initializeGroups() {

		List<Group> groups = new ArrayList<Group>();
		User user = userService.getPrincipal();
		if (user != null) {
			groups = groupService.getGroups(user.getId());
		}
		return groups;
	}

	private String getUrl(HttpServletRequest request) {

		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}

}