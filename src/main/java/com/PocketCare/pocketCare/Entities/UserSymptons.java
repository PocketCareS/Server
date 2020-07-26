package com.PocketCare.pocketCare.Entities;

import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "UserSymptoms")
public class UserSymptons {

	@Id
	@EmbeddedId
	private EmbeddedSymptomsId symptomsId;
	private List<String> usersSymptoms;
	private List<String> roommatesSymptoms;
	private List<String> othersSymptoms;
	
	public UserSymptons(EmbeddedSymptomsId symptomsId, List<String> usersSymptoms, List<String> roommatesSymptoms,
			List<String> othersSymptoms) {
		super();
		this.symptomsId = symptomsId;
		this.usersSymptoms = usersSymptoms;
		this.roommatesSymptoms = roommatesSymptoms;
		this.othersSymptoms = othersSymptoms;
	}

	public List<String> getRoommatesSymptoms() {
		return roommatesSymptoms;
	}

	public void setRoommatesSymptoms(List<String> roommatesSymptoms) {
		this.roommatesSymptoms = roommatesSymptoms;
	}

	public List<String> getUsersSymptoms() {
		return usersSymptoms;
	}

	public void setUsersSymptoms(List<String> usersSymptoms) {
		this.usersSymptoms = usersSymptoms;
	}

	public List<String> getOthersSymptoms() {
		return othersSymptoms;
	}

	public void setOthersSymptoms(List<String> othersSymptoms) {
		this.othersSymptoms = othersSymptoms;
	}

	public EmbeddedSymptomsId getSymptomsId() {
		return symptomsId;
	}

	public void setSymptomsId(EmbeddedSymptomsId symptomsId) {
		this.symptomsId = symptomsId;
	}
	
	

}
