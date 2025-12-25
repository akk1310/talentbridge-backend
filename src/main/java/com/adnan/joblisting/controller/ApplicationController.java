package com.adnan.joblisting.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adnan.joblisting.model.Application;
import com.adnan.joblisting.model.Candidate;
import com.adnan.joblisting.model.Post;
import com.adnan.joblisting.repository.ApplicationRepository;
import com.adnan.joblisting.repository.CandidateRepository;
import com.adnan.joblisting.repository.PostRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/candidate")
public class ApplicationController {

    @Autowired
    private ApplicationRepository appRepo;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private CandidateRepository candRepo;

    @PostMapping("/apply/{jobId}")
    public String apply(@PathVariable String jobId, HttpServletRequest request) {

        String candidateId = (String) request.getAttribute("candidateId");

        if (candidateId == null) {
            throw new RuntimeException("Candidate not authenticated");
        }

        Candidate candidate = candRepo.findById(new ObjectId(candidateId))
            .orElseThrow(() -> new RuntimeException("Candidate not found"));

        Post job = postRepo.findById(new ObjectId(jobId))
            .orElseThrow(() -> new RuntimeException("Job not found"));

        if (appRepo.existsByJobIdAndCandidateId(
                job.getObjectId(),
                candidate.getObjectId()
        )) {
            throw new RuntimeException("Already applied");
        }


        Application app = new Application();
//        app.setJobId(new ObjectId(job.getId()));
//        app.setEmployerId(job.getEmployerId());
//        app.setCandidateName(candidate.getName());
//        app.setResumeUrl(candidate.getResumeUrl());
        app.setJobId(job.getObjectId());               // ✅ ObjectId
        app.setCandidateId(candidate.getObjectId()); //🔴 REQUIRED
        app.setEmployerId(job.getEmployerId());
        app.setCandidateName(candidate.getName());
        app.setResumeUrl(candidate.getResumeUrl());
        app.setJobProfile(job.getProfile());

        appRepo.save(app);

        return "Applied successfully";
    }
    
    @GetMapping("/applications")
    public List<Application> getCandidateApplications(HttpServletRequest request) {

        String candidateId = (String) request.getAttribute("candidateId");

        if (candidateId == null) {
            throw new RuntimeException("Candidate not authenticated");
        }

        return appRepo.findByCandidateId(new ObjectId(candidateId));
    }




    
//    @GetMapping("/applications")
//    public List<Application> getApplications(
//        HttpServletRequest request
//    ) {
//        String employerId =
//            (String) request.getAttribute("employerId");
//
//        return appRepo.findByEmployerId(employerId);
//    }

}
