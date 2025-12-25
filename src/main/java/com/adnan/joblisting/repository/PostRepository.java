package com.adnan.joblisting.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.adnan.joblisting.model.Post;

public interface PostRepository extends MongoRepository<Post, ObjectId> {

    List<Post> findByEmployerId(String employerId);

    long countByEmployerId(String employerId);
}
