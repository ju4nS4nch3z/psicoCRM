package com.psicocrm.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Administrator extends User {

	@Column(name = "school")
	private String school;

	@Column(name = "address")
	private String address;

	@Column(name = "phone")
	private String phone;

	@Column(name = "cif")
	private String cif;

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getOrganization() {
		return this.school;
	}

}
