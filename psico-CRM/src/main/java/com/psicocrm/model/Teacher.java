package com.psicocrm.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Teacher extends User {

	@Column(name = "name")
	private String name;

	@Column(name = "surname1")
	private String surname1;

	@Column(name = "surname2")
	private String surname2;

	@Column(name = "phone")
	private String phone;

	@ManyToMany
	@JoinTable(name = "teacher_group", 
	joinColumns = @JoinColumn(name = "teacher_id"), 
	inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Set<Group> groups = new HashSet<Group>();

	@ManyToOne
	@JoinColumn(name = "administrator_id")
	private Administrator administrator;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname1() {
		return surname1;
	}

	public void setSurname1(String surname1) {
		this.surname1 = surname1;
	}

	public String getSurname2() {
		return surname2;
	}

	public void setSurname2(String surname2) {
		this.surname2 = surname2;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public Administrator getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}

	public String getOrganization() {
		return this.getAdministrator().getSchool();
	}

	public List<Long> getGroupsIds() {
		List<Long> groupsIds = new ArrayList<Long>();

		for (Group g : this.groups) {
			groupsIds.add(g.getId());
		}

		return groupsIds;
	}

}
