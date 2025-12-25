package com.adnan.joblisting.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "candidates")
public class Candidate {

    @Id
    private ObjectId id;

    private String name;
    private String email;
    private String password; // bcrypt
    private String resumeUrl; // PDF path

    @JsonProperty("id")
    public String getId() {
        return id != null ? id.toHexString() : null;
    }
    public ObjectId getObjectId() {
	    return id;
	}

    public void setId(ObjectId id) {
        this.id = id;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResumeUrl() {
		return resumeUrl;
	}

	public void setResumeUrl(String resumeUrl) {
		this.resumeUrl = resumeUrl;
	}

    // getters & setters
    
}
