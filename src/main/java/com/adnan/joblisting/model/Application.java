package com.adnan.joblisting.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;


@Document(collection = "applications")
public class Application {

    @Id
    private ObjectId id;

    private ObjectId jobId;
    private ObjectId candidateId;
    private String employerId;

    private String candidateName;
    private String resumeUrl;
    private String jobProfile;


    private Date appliedAt = new Date();
    

    @JsonProperty("jobId")
    public String getJobIdString() {
        return jobId != null ? jobId.toHexString() : null;
    }


	public String getJobProfile() {
		return jobProfile;
	}

	public void setJobProfile(String jobProfile) {
		this.jobProfile = jobProfile;
	}

	public ObjectId getId() {
		return id;
	}
	
	

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getJobId() {
		return jobId;
	}

	public void setJobId(ObjectId jobId) {
		this.jobId = jobId;
	}

	public ObjectId getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(ObjectId candidateId) {
		this.candidateId = candidateId;
	}

	public String getEmployerId() {
		return employerId;
	}

	public void setEmployerId(String employerId) {
		this.employerId = employerId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getResumeUrl() {
		return resumeUrl;
	}

	public void setResumeUrl(String resumeUrl) {
		this.resumeUrl = resumeUrl;
	}

	public Date getAppliedAt() {
		return appliedAt;
	}

	public void setAppliedAt(Date appliedAt) {
		this.appliedAt = appliedAt;
	}

    // getters & setters
    
}
