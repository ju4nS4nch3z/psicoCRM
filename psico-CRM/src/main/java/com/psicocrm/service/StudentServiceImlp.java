package com.psicocrm.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.psicocrm.dao.StudentDAO;
import com.psicocrm.model.Group;
import com.psicocrm.model.Student;
import com.psicocrm.model.User;

@Service
public class StudentServiceImlp implements StudentService {
	private static final int PAGE_SIZE = 10;
	
	@Autowired
	private StudentDAO studentRepository;
/*
	@Override
	public Page<Student> getTeachers(int pageNumber, Long group_id) {
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "name");
		return studentRepository.findByGroup_Id(group_id, pageable);
	}
*/
	@Override
	public void save(Student student) {
		studentRepository.save(student);

	}

	@Override
	public void delete(long id) {
		studentRepository.delete(id);

	}

	@Override
	public Student getStudentById(long id) {
		return studentRepository.findOne(id);
	}


	@Override
	public Page<Student> getStudents(int pageNumber, List<Long> groupsIds) {
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "name");
		return studentRepository.findByGroup_IdIn(groupsIds, pageable);
	}

	@Override
	public List<Student> getStudentsByGroup(List<Long> groupsIds) {
		return studentRepository.findByGroup_IdIn(groupsIds);
	}



}
