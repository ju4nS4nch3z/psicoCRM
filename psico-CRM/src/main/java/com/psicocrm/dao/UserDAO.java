package com.psicocrm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.psicocrm.model.User;

public interface UserDAO extends JpaRepository<User, Long> {
	User findByMail(String email);

}
