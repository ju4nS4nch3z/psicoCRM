package com.psicocrm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.psicocrm.model.Teacher;

public interface TeacherDAO extends JpaRepository<Teacher, Long> {

	Page<Teacher> findByAdministrator_Id(long administrator_id, Pageable pageable);

	List<Teacher> findByAdministrator_Id(long administrator_id);

}
