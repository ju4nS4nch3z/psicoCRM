package com.psicocrm.service;

import java.io.IOException;
import java.util.Locale;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.itextpdf.text.DocumentException;
import com.psicocrm.model.Answer;
import com.psicocrm.model.Option;
import com.psicocrm.model.Question;
import com.psicocrm.model.Questionnaire;
import com.psicocrm.model.Questionnaire_Done;

public interface QuestionaireService {

	public Page<Questionnaire_Done> getStudentQuestionnaires(int pageNum, long questionnaire_id, long student_id);
		
	public Questionnaire getQuestionnaireById(long id);
	
	public void saveQD(Questionnaire_Done questionnaire_done);

	public void saveAnswer(Answer answer);

	public Option getOptionById(Long id);

	public Question getQuestionById(Long id);

	public Set<Question> getQuestionsByIdAndLanguage(Long id, String language);

	public Questionnaire_Done getQuestionnaireDoneById(Long qDoneId);

	public byte[] writePDF(Questionnaire_Done qdone, Locale locale) throws DocumentException, IOException;
}
