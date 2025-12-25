package com.adnan.joblisting.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.adnan.joblisting.model.Post;
import com.adnan.joblisting.repository.PostRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/employer")
public class EmployerDashboardController {

    @Autowired
    private PostRepository postRepo;

    // 1️⃣ Create Job
    @PostMapping("/job")
    public Post createJob(
            HttpServletRequest request,
            @RequestBody Post post) {

        String employerId = (String) request.getAttribute("employerId");

        post.setEmployerId(employerId);
        return postRepo.save(post);
    }

    // 2️⃣ Get Employer Jobs
    @GetMapping("/jobs")
    public List<Post> getEmployerJobs(HttpServletRequest request) {

        String employerId = (String) request.getAttribute("employerId");
        return postRepo.findByEmployerId(employerId);
    }

    // 3️⃣ Count Jobs
    @GetMapping("/jobs/count")
    public long countEmployerJobs(HttpServletRequest request) {

        String employerId = (String) request.getAttribute("employerId");
        return postRepo.countByEmployerId(employerId);
    }

    // 4️⃣ Delete Job
    @DeleteMapping("/job/{jobId}")
    public String deleteJob(
            @PathVariable String jobId,
            HttpServletRequest request) {

        String employerId = (String) request.getAttribute("employerId");

        Post post = postRepo.findById(new ObjectId(jobId))
                .orElseThrow(() -> new RuntimeException("Job not found"));


        if (!post.getEmployerId().equals(employerId)) {
            throw new RuntimeException("Unauthorized delete");
        }

        postRepo.deleteById(new ObjectId(jobId));
        return "Job deleted";
    }
}
