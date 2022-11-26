package com.abc.restApi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Donator {

	@Id
	@Min(1)
	private int donatorId;
	
	@NotNull(message = "Name should not be null")
	private String donatorName;
	
	@NotNull(message = "Gender should not be null")
	private String donatorGender;
	
	@Min(18)
	@Max(65)
	private int donatorAge;
	
	@NotNull
	private String donatorBloodGroup;
	@NotNull(message = "Blood group is neccessary")
	
	public Donator() {
		
	}

	public Donator(int donatorId, String donatorName, String donatorGender, int donatorAge, String donatorBloodGroup) {
		super();
		this.donatorId = donatorId;
		this.donatorName = donatorName;
		this.donatorGender = donatorGender;
		this.donatorAge = donatorAge;
		this.donatorBloodGroup = donatorBloodGroup;
	}

	public int getDonatorId() {
		return donatorId;
	}

	public void setDonatorId(int donatorId) {
		this.donatorId = donatorId;
	}

	public String getDonatorName() {
		return donatorName;
	}

	public void setDonatorName(String donatorName) {
		this.donatorName = donatorName;
	}

	public String getDonatorGender() {
		return donatorGender;
	}

	public void setDonatorGender(String donatorGender) {
		this.donatorGender = donatorGender;
	}

	public int getDonatorAge() {
		return donatorAge;
	}

	public void setDonatorAge(int donatorAge) {
		this.donatorAge = donatorAge;
	}

	public String getDonatorBloodGroup() {
		return donatorBloodGroup;
	}

	public void setDonatorBloodGroup(String donatorBloodGroup) {
		this.donatorBloodGroup = donatorBloodGroup;
	}
	
	
}
