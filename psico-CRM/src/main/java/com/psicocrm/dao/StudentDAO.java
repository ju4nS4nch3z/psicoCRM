package com.psicocrm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.psicocrm.model.Group;
import com.psicocrm.model.Student;

public interface StudentDAO extends JpaRepository<Student, Long> {

	Page<Student> findByGroup_Id(Long group_id, Pageable pageable);

	Page<Student> findByGroup_IdIn(List<Long> groupsIds, Pageable pageable);
	
	List<Student> findByGroup_IdIn(List<Long> groupsIds);


}
