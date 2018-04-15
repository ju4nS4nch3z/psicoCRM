package com.psicocrm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.psicocrm.model.Questionnaire_Done;

public interface Questionnaire_DoneDAO extends JpaRepository<Questionnaire_Done, Long> {

	Page<Questionnaire_Done> findByStudent_IdAndQuestionnaire_Id(long student_id, long questionnaire_id, Pageable pageable);

	Page<Questionnaire_Done> findByStudent_Id(long student_id, Pageable pageable);
}
