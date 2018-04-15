package com.psicocrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.psicocrm.model.Administrator;
import com.psicocrm.model.Group;
import com.psicocrm.model.Teacher;
import com.psicocrm.model.User;
import com.psicocrm.service.GroupService;
import com.psicocrm.service.UserService;

@Controller
@RequestMapping("/groups")
public class GroupController {

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index() {
		return "redirect:/groups/list/1";
	}
	
	@RequestMapping(value = "/list/{pageNum}", method = RequestMethod.GET)
	public ModelAndView index(@PathVariable int pageNum) {
		ModelAndView model = new ModelAndView("groups_list");
		User user = getPrincipal();
		model.addObject("user", user);

		Page<Group> page = groupService.getGroups(pageNum, user.getId());

		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, page.getTotalPages());

		model.addObject("page", page);
		model.addObject("beginIndex", begin);
		model.addObject("endIndex", end);
		model.addObject("currentIndex", current);
		
		model.addObject("path", "/groups/list/");

		return model;
	/*	ModelAndView model = new ModelAndView("groups_list");

		model.addObject("list", groupService.findAll());
		model.addObject("user", getPrincipal());

		return model;
		*/
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String userRegister(@ModelAttribute("groupForm") Group group) {
		if (group != null) {
			group.setAdministrator((Administrator) getPrincipal());
			groupService.save(group);
		}
		return "redirect:/groups/list/1";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteUsers(@PathVariable long id) {
		groupService.delete(id);
		
		return "redirect:/groups/list/1";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") long id) {
		ModelAndView model = new ModelAndView("groups_form");

		Group group = null;
		if (id > 0) {
			group = groupService.getOne(id);
		} else {
			group = new Group();
		}
		model.addObject("groupForm", group);

		model.addObject("user", getPrincipal());

		return model;
	}

	private User getPrincipal() {
		User user = new User();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			user = userService.findByMail(((UserDetails) principal).getUsername());
		} else {
			user = userService.findByMail(principal.toString());
		}
		return user;
	}
}
