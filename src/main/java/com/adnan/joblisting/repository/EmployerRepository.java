package com.adnan.joblisting.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.adnan.joblisting.model.Employer;

public interface EmployerRepository extends MongoRepository<Employer, String> {
	Optional<Employer> findByEmail(String email);
}
