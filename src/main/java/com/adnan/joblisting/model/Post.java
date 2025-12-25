package com.adnan.joblisting.model;

import java.util.Arrays;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "JobPost")
public class Post {
	
	@Id
	@JsonProperty("id")
	private ObjectId id;   // ✅ String, NOT ObjectId
	private String profile;
	private String desc;
	private int exp;
	private String location;
	private String[] techs;
	private String employerId;
	
	
	
	public Post() {
		super();
	}
	
	

	public Post(String profile, String desc, int exp, String location, String[] techs) {
		super();
		this.profile = profile;
		this.desc = desc;
		this.exp = exp;
		this.location = location;
		this.techs = techs;
	}
	public ObjectId getObjectId() {
	    return id;
	}

	 public String getId() {
	        return id != null ? id.toHexString() : null;
	    }

	    public void setId(ObjectId id) {
	        this.id = id;
	    }


	public String getEmployerId() {
		return employerId;
	}



	public void setEmployerId(String employerId) {
		this.employerId = employerId;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public String[] getTechs() {
		return techs;
	}
	public void setTechs(String[] techs) {
		this.techs = techs;
	}
	@Override
	public String toString() {
		return "Post [profile=" + profile + ", desc=" + desc + ", exp=" + exp + ", techs=" + Arrays.toString(techs)
				+ "]";
	}
	
	
}
