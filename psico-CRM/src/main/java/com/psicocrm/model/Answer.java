package com.psicocrm.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "answers")
public class Answer {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "option_id", referencedColumnName="id")
	private Option option;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "question_id", referencedColumnName = "id"),
	@JoinColumn(name = "question_language", referencedColumnName = "language") })
	private Question question;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "questionnaire_done_id", referencedColumnName="id")
	private Questionnaire_Done questionnaire_done;

	public Questionnaire_Done getQuestionnaire_done() {
		return questionnaire_done;
	}

	public void setQuestionnaire_done(Questionnaire_Done questionnaire_done) {
		this.questionnaire_done = questionnaire_done;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}
