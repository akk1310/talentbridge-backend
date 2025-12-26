package com.adnan.joblisting.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adnan.joblisting.dto.PostFilterRequest;
import com.adnan.joblisting.dto.PostPageResponse;
import org.springframework.data.domain.PageRequest;
import com.adnan.joblisting.model.Post;
import com.adnan.joblisting.repository.PostRepository;
import com.adnan.joblisting.repository.SearchRepository;


@RestController
public class PostController {
	
	@Autowired
	PostRepository repo;
	
	@Autowired
	SearchRepository srepo;
	
	@GetMapping("/posts")
	public List<Post> getAllPosts(){
		return repo.findAll();
	}
//	@GetMapping("/posts/{text}")
//	public List<Post> search(@PathVariable String text){
//		return srepo.findByText(text);
//	}
//	@PostMapping("/posts/filter")
//	public List<Post> filterPosts(@RequestBody PostFilterRequest filter) {
//	    return srepo.searchByFilters(filter);
//	}
	
	@PostMapping("/posts/filter")
	public PostPageResponse filterPosts(@RequestBody PostFilterRequest filter) {


		boolean noFilters =
		        (filter.getProfile() == null || filter.getProfile().isBlank()) &&
		        (filter.getLocation() == null || filter.getLocation().isBlank()) &&
		        (filter.getTech() == null || filter.getTech().isBlank()) &&
		        filter.getMinExp() == null &&
		        filter.getMaxExp() == null;


	    if (noFilters) {
	        int page = filter.getPage();
	        int size = filter.getSize();

	        long total = repo.count();

	        List<Post> posts = repo.findAll(
	                PageRequest.of(page, size)
	        ).getContent();

	        return new PostPageResponse(posts, total, page, size);
	    }

	    // 🔽 Only use Atlas Search if filters exist
	    List<Post> posts = srepo.searchByFilters(filter);
	    long total = srepo.countByFilters(filter);

	    return new PostPageResponse(posts, total, filter.getPage(), filter.getSize());
	}


	
	@PostMapping("/post")
	public Post addPost(@RequestBody Post post){
		return repo.save(post);
	}
}
