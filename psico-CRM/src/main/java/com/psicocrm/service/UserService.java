package com.psicocrm.service;

import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Page;

import com.psicocrm.model.Administrator;
import com.psicocrm.model.Student;
import com.psicocrm.model.Teacher;
import com.psicocrm.model.User;

public interface UserService {
	
	public User findByMail(String mail);

	public void save(User usuari);

	boolean validateUser(String mail, String password);

	public Iterable<User> listAllUsers();

	void deleteUser(long id);

	public User getUserById(long id);

	public void update(User user);

	/**/
	public List<Teacher> listAllTeachers();

	public Page<Teacher> getTeachers(int ipage, long administrator_id);

	public boolean resetPassword(User usuari, String url, Locale locale);
	
	public User getPrincipal();
	
	public String generatePassword();

}
