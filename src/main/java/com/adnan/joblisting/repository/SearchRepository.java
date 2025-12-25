package com.adnan.joblisting.repository;

import java.util.List;

import com.adnan.joblisting.dto.PostFilterRequest;
import com.adnan.joblisting.model.Post;

public interface SearchRepository {
//	public List<Post> findByText(String text);
	List<Post> searchByFilters(PostFilterRequest filter);
	long countByFilters(PostFilterRequest filter);

		
}
