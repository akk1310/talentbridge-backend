package com.adnan.joblisting.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.adnan.joblisting.dto.CandidateLoginRequest;
import com.adnan.joblisting.dto.CandidateRegisterRequest;
import com.adnan.joblisting.model.Candidate;
import com.adnan.joblisting.repository.CandidateRepository;
import com.adnan.joblisting.security.JwtUtil;
import com.cloudinary.Cloudinary;

@RestController
public class CandidateController {

    @Autowired
    private CandidateRepository crepo;
    
    @Autowired
    private Cloudinary cloudinary;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/candidate/register")
    public Candidate registerCandidate(
            @RequestPart("data") CandidateRegisterRequest req,
            @RequestPart("resume") MultipartFile resume) {

        if (crepo.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // ✅ Upload resume to Cloudinary
        Map uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(
                    resume.getBytes(),
                    Map.of(
                    		"resource_type", "raw",
                            "folder", "resumes",
                            "use_filename", true,
                            "unique_filename", true
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException("Resume upload failed");
        }

        String resumeUrl = uploadResult.get("secure_url").toString();

        Candidate c = new Candidate();
        c.setName(req.getName());
        c.setEmail(req.getEmail());
        c.setPassword(
            new BCryptPasswordEncoder().encode(req.getPassword())
        );
        c.setResumeUrl(resumeUrl);

        Candidate saved = crepo.save(c);
        saved.setPassword(null); // NEVER expose password
        return saved;
    }
    
    @PostMapping("/candidate/login")
    public Map<String, Object> loginCandidate(
            @RequestBody CandidateLoginRequest req) {

        Candidate candidate = crepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(req.getPassword(), candidate.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

//        String token = JwtUtil.generateToken(candidate.getId());
        String token= JwtUtil.generateToken(candidate.getId());


        candidate.setPassword(null);

        Map<String, Object> response = new HashMap<>();
        response.put("candidate", candidate);
        response.put("token", token);

        return response;
    }

    
}
