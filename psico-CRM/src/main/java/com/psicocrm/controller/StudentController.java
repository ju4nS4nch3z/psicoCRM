package com.psicocrm.controller;

import java.util.List;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.psicocrm.model.Group;
import com.psicocrm.model.Questionnaire_Done;
import com.psicocrm.model.Student;
import com.psicocrm.model.Teacher;
import com.psicocrm.model.User;
import com.psicocrm.service.GroupService;
import com.psicocrm.service.StudentService;
import com.psicocrm.service.UserService;

@Controller
@RequestMapping("/students")
@SessionAttributes("groupList")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index() {
		return "redirect:/students/list/1";
	}

	@RequestMapping(value = "/list/{pageNum}", method = RequestMethod.GET)
	public ModelAndView index(@PathVariable int pageNum) {
		ModelAndView model = new ModelAndView("students_list");
		User user = getPrincipal();
		model.addObject("user", user);

		List<Long> groupsIds = ((Teacher) user).getGroupsIds();

		Page<Student> page = studentService.getStudents(pageNum, groupsIds);

		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, page.getTotalPages());

		model.addObject("page", page);
		model.addObject("beginIndex", begin);
		model.addObject("endIndex", end);
		model.addObject("currentIndex", current);

		model.addObject("path", "/students/list/");

		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String userRegister(@ModelAttribute("studentForm") Student student, HttpServletRequest request) {

		if (student != null) {

			studentService.save(student);

		}
		return "redirect:/students/list/1";
	}

	@RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") long id) {
		ModelAndView model = new ModelAndView("students_form");
		Student student = null;
		if (id > 0) {
			student = studentService.getStudentById(id);
		} else {
			student = new Student();
		}

		model.addObject("studentForm", student);

		try {
			model.addObject("studentGroups", new ObjectMapper().writeValueAsString((student.getGroup())));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		model.addObject("user", getPrincipal());

		return model;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteUsers(@PathVariable long id) {

		studentService.delete(id);

		return "redirect:/students/list/1";
	}

	@ModelAttribute("groupList")
	public List<Group> initializeGroups() {
		User user = getPrincipal();
		List<Group> list = new ArrayList<Group>(((Teacher) user).getGroups());
		return list;
	}

	@RequestMapping(value = { "/stats/{id}" }, method = RequestMethod.GET)
	public ModelAndView stats(@PathVariable("id") long id) {
		ModelAndView model = new ModelAndView("statistics");
		Student student = null;
		if (id > 0) {
			student = studentService.getStudentById(id);
		} else {
			student = new Student();
		}

		model.addObject("studentForm", student);
		User user = getPrincipal();
		if (!user.isAdmin()) {
			model.addObject("studentList", studentService.getStudentsByGroup(((Teacher) user).getGroupsIds()));
		}
		try {
			model.addObject("studentGroups", new ObjectMapper().writeValueAsString((student.getGroup())));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		model.addObject("user", user);

		return model;
	}

	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public @ResponseBody String add(HttpServletRequest request, HttpServletResponse response) throws Exception {

		@SuppressWarnings("unused")
		String type = request.getParameter("type");
		Long id = Long.parseLong(request.getParameter("id"));

		Student student = studentService.getStudentById(id);

		JsonArray labels = new JsonArray();
		JsonArray suArray = new JsonArray();
		JsonArray maArray = new JsonArray();
		JsonArray raArray = new JsonArray();
		JsonObject js = new JsonObject();
		List<Questionnaire_Done> qdoneList = student.getQuestionnaires_done();

		int i = 0;
		for (Questionnaire_Done qd : qdoneList) {
			i++;
			// labels.add(i);
			String date = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(qd.getDate());
			// String date = new SimpleDateFormat("MM/dd/yyyy").format(qd.getDate());
			labels.add(date);
			switch (qd.getQuestionnaire().getName()) {
			case "Suicidio": {
				JsonObject j = new JsonObject();// "{x: 10:00, y: 127}";

				j.addProperty("x", date);
				j.addProperty("y", qd.getResult());
				// suArray.add(qd.getResult());
				suArray.add(j);
				break;
			}
			case "Maltrato": {
				maArray.add(qd.getResult());
				break;
			}
			case "Radicalismo": {
				raArray.add(qd.getResult());
				break;
			}
			}
		}
		js.add("labels", labels);
		js.add("suArray", suArray);
		js.add("maArray", maArray);
		js.add("raArray", raArray);

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		String jsonString = gson.toJson(js);

		return jsonString;
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
