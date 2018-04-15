package com.psicocrm.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "questionnaires")
public class Questionnaire {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Expose
	@Column(name = "name", nullable = false)
	@NotEmpty(message = "Please provide an name")
	private String name;

	@Column(name = "description")
	private String description;

	@OneToMany(mappedBy = "questionnaire")
	@OrderBy("position")
	private Set<Question> questions;

	//@OneToMany(mappedBy = "questionnaire")
	//private Set<Questionnaire_Done> questionnaires_done;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	/*
	public Set<Questionnaire_Done> getQuestionnaires_done() {
		return questionnaires_done;
	}

	public void setQuestionnaires_done(Set<Questionnaire_Done> questionnaires_done) {
		this.questionnaires_done = questionnaires_done;
	}
	*/

}
