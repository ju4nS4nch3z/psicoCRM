package com.psicocrm.service;

import java.util.List;
import org.springframework.data.domain.Page;

import com.psicocrm.model.Group;
import com.psicocrm.model.Student;

public interface StudentService {


	void save(Student student);

	void delete(long id);

	Student getStudentById(long id);

	Page<Student> getStudents(int pageNum, List<Long> groupsIds);

	List<Student> getStudentsByGroup(List<Long> initializeGroups);

}
