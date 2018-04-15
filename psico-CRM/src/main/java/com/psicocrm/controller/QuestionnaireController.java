package com.psicocrm.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.itextpdf.text.DocumentException;
import com.psicocrm.model.Answer;
import com.psicocrm.model.Option;
import com.psicocrm.model.Question;
import com.psicocrm.model.Questionnaire;
import com.psicocrm.model.Questionnaire_Done;
import com.psicocrm.model.Student;
import com.psicocrm.model.User;
import com.psicocrm.service.EmailService;
import com.psicocrm.service.QuestionaireService;
import com.psicocrm.service.StudentService;
import com.psicocrm.service.UserService;

@Controller
@RequestMapping("/questionnaires")
public class QuestionnaireController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private QuestionaireService questionnaireService;

	@Autowired
	private UserService userService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index() {
		return "redirect:/questionnaires/list/1";
	}

	@RequestMapping(value = "/list/{questionnaire_id}/{student_id}/{pageNum}", method = RequestMethod.GET)
	public ModelAndView index(@PathVariable int pageNum, @PathVariable long questionnaire_id,
			@PathVariable long student_id) {

		ModelAndView model = new ModelAndView("questionnaires_list");
		User user = getPrincipal();
		model.addObject("user", user);

		Page<Questionnaire_Done> page = questionnaireService.getStudentQuestionnaires(pageNum, questionnaire_id,
				student_id);

		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, page.getTotalPages());

		model.addObject("page", page);
		model.addObject("beginIndex", begin);
		model.addObject("endIndex", end);
		model.addObject("currentIndex", current);

		model.addObject("student_id", student_id);
		model.addObject("path", "/questionnaires/list/");

		return model;
	}

	@RequestMapping(value = "/new/{student_id}/{type}", method = RequestMethod.GET)
	public ModelAndView index(Locale locale, @PathVariable long student_id, @PathVariable int type) {

		ModelAndView model = new ModelAndView("questionnaire_form");
		User user = getPrincipal();
		model.addObject("user", user);

		Questionnaire_Done qdone = new Questionnaire_Done();

		qdone.setLanguage(locale.getLanguage());
		qdone.setStudent(studentService.getStudentById(student_id));
		Questionnaire questionnaire = questionnaireService.getQuestionnaireById(type);

		questionnaire.setQuestions(
				questionnaireService.getQuestionsByIdAndLanguage(questionnaire.getId(), locale.getLanguage()));

		qdone.setQuestionnaire(questionnaire);
		qdone.setDate(new Timestamp(new Date().getTime()));

		for (Question q : qdone.getQuestionnaire().getQuestions()) {
			Answer a = new Answer();
			a.setQuestionnaire_done(qdone);
			a.setQuestion(q);
			a.setOption(new Option());
			qdone.addAnswer(a);
		}
		model.addObject("testResult", "");
		model.addObject("qDoneForm", qdone);

		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(Locale locale, @ModelAttribute("qDoneForm") Questionnaire_Done qdone) {

		ModelAndView model = new ModelAndView("questionnaire_form");
		User user = getPrincipal();
		model.addObject("user", user);

		int result = 0;

		for (Answer answer : qdone.getAnswers()) {

			Option option = questionnaireService.getOptionById(answer.getOption().getId());
			answer.setOption(option);
			Question question = questionnaireService.getQuestionById(answer.getQuestion().getId());
			answer.setQuestion(question);
			answer.setQuestionnaire_done(qdone);

			result += answer.getOption().getValue();

			questionnaireService.saveAnswer(answer);
		}
		qdone.setResult(result);
		qdone.setDate(new Timestamp(new Date().getTime()));
		questionnaireService.saveQD(qdone);

		model.addObject("qDoneForm", qdone);

		String res = "";
		if (isBetween(qdone.getResult(), 0, 20)) {
			res = "Nor";
		} else if (isBetween(qdone.getResult(), 21, 30)) {
			res = "Seg";
		} else if (isBetween(qdone.getResult(), 31, 40)) {
			res = "Rie";
		}
		model.addObject("testResult",
				messageSource.getMessage("resultQ" + qdone.getQuestionnaire().getId() + res, null, locale));

		return model;

		/*
		 * return "redirect:/questionnaires/list/" + qdone.getQuestionnaire().getId() +
		 * "/" + qdone.getStudent().getId() + "/1";
		 */
	}

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public @ResponseBody String send(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Long qDoneId = Long.parseLong(request.getParameter("qDone"));
		String emails = request.getParameter("emails");
		boolean sendParents = Boolean.parseBoolean(request.getParameter("sendParents"));

		Questionnaire_Done qdone = questionnaireService.getQuestionnaireDoneById(qDoneId);

		ByteArrayResource resource = null;
		JsonObject js = new JsonObject();
		try {
			resource = new ByteArrayResource(questionnaireService.writePDF(qdone, request.getLocale()));
			js = emailService.sendQuestionnaire(qdone, emails, sendParents, resource, request.getLocale());
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		String jsonString = gson.toJson(js);

		return jsonString;
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity<Resource> download(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		Long qDoneId = Long.parseLong(request.getParameter("qDone"));
		Questionnaire_Done qdone = questionnaireService.getQuestionnaireDoneById(qDoneId);
		String date = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(qdone.getDate());
		ByteArrayResource resource = null;
		try {
			resource = new ByteArrayResource(questionnaireService.writePDF(qdone, request.getLocale()));
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String
				.format("inline; filename=\"" + qdone.getQuestionnaire().getDescription() + "_" + date + ".pdf\""));

		return ResponseEntity.ok().headers(headers).contentLength(resource.contentLength())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	public static boolean isBetween(int x, int lower, int upper) {
		return lower <= x && x <= upper;
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
