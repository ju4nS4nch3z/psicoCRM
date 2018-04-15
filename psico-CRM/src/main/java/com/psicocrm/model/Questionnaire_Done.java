package com.psicocrm.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "Questionnaires_Done")
public class Questionnaire_Done {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "language")
	private String language;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "student_id", referencedColumnName = "id")
	private Student student;

	@Expose
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "questionnaire_id", referencedColumnName = "id")
	private Questionnaire questionnaire;

	@OneToMany(mappedBy = "questionnaire_done", cascade = CascadeType.MERGE)
	private List<Answer> answers;
	
	@Expose
	@Column(name = "date")
	private Timestamp date;

	@Expose
	@Column(name = "result")
	private int result;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public void addAnswer(Answer answer) {
		if (answer != null) {
			if (answers == null) {
				answers = new ArrayList<Answer>();
			}
			answers.add(answer);
			answer.setQuestionnaire_done(this);
		}
	}

}
