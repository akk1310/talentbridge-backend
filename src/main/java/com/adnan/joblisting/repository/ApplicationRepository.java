package com.adnan.joblisting.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.adnan.joblisting.model.Application;

public interface ApplicationRepository
extends MongoRepository<Application, ObjectId> {

	boolean existsByJobIdAndCandidateId(
		ObjectId jobId,
		ObjectId candidateId
	);

	List<Application> findByEmployerId(String employerId);
	List<Application> findByCandidateId(ObjectId candidateId);

}

