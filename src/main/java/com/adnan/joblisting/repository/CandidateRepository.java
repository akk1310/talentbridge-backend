package com.adnan.joblisting.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.adnan.joblisting.model.Candidate;

public interface CandidateRepository
extends MongoRepository<Candidate, ObjectId> {

Optional<Candidate> findByEmail(String email);
}
