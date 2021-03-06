package com.psicocrm.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import com.psicocrm.dao.TeacherDAO;
import com.psicocrm.dao.UserDAO;
import com.psicocrm.model.Administrator;
import com.psicocrm.model.Teacher;
import com.psicocrm.model.User;
import com.psicocrm.service.EmailService.TypeEmail;

@Service
public class UserServiceImpl implements UserService {
	private static final int PAGE_SIZE = 10;

	@Autowired
	private UserDAO userRepository;
	@Autowired
	private TeacherDAO teacherRepository;
	@Autowired
	private EmailService emailService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User findByMail(String mail) {
		return userRepository.findByMail(mail);
	}

	@Override
	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	@Override
	public Iterable<User> listAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public void deleteUser(long id) {
		userRepository.delete(id);

	}

	@Override
	public User getUserById(long id) {
		return userRepository.findOne(id);
	}

	@Override
	public void update(User user) {
		userRepository.save(user);
	}



	@Override
	public Page<Teacher> getTeachers(int pageNumber, long administrator_id) {
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "name");
		return teacherRepository.findByAdministrator_Id(administrator_id, pageable);
	}
	
	@Override
	public List<Teacher> listAllTeachers(long administrator_id) {
		return teacherRepository.findByAdministrator_Id(administrator_id);
	}

	@Override
	public boolean validateUser(String mail, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean resetPassword(User usuari, String url, Locale locale) {

		User user = findByMail(usuari.getMail());

		if (user == null) {
			return false;
		} else {
			String pass = generatePassword();
			user.setPassword(pass);

			save(user);

			emailService.sendPassword(TypeEmail.NEW_PASSWORD, user, pass, url, locale);

			return true;
		}

	}

	@Override
	public User getPrincipal() {
		User user = new User();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			user = findByMail(((UserDetails) principal).getUsername());
		} else {
			user = findByMail(principal.toString());
		}
		return user;
	}

	@Override
	public String generatePassword() {
		SecureRandom random = new SecureRandom();
		String dic = "abcdefghijklmnopqrstuvwxyz0123456789";
		String result = "";
		for (int i = 0; i < 8; i++) {
			int index = random.nextInt(dic.length());
			result += dic.charAt(index);
		}
		return result;
	}

	@Override
	public void validate(Administrator user, BindingResult errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mail", "error.form.NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.form.NotEmpty");

		if (user.getMail().length() > 254) {
			String[] args = { "255" };
			errors.rejectValue("mail", "error.form.length", args, null);
		}
		if (user.getSchool().length() > 254) {
			String[] args = { "255" };
			errors.rejectValue("school", "error.form.length", args, null);
		}
		if (user.getAddress().length() > 254) {
			String[] args = { "255" };
			errors.rejectValue("address", "error.form.length", args, null);
		}
		if (user.getPhone().length() > 254) {
			String[] args = { "255" };
			errors.rejectValue("phone", "error.form.length", args, null);
		}
		if (user.getCif().length() > 254) {
			String[] args = { "255" };
			errors.rejectValue("cif", "error.form.length", args, null);
		}

		if (userRepository.findByMail(user.getMail()) != null) {
			errors.rejectValue("mail", "error.form.duplicateUser");
		}

		if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
			errors.rejectValue("password", "error.form.password.length");
		} else {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "school", "error.form.NotEmpty");
		}

	}


}
