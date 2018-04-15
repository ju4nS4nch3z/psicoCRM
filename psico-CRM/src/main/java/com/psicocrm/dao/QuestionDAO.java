package com.psicocrm.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psicocrm.model.Question;

public interface QuestionDAO extends JpaRepository<Question, Long> {

	Set<Question> getQuestionsByQuestionnaire_IdAndLanguage(Long id, String language);


}
