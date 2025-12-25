package com.adnan.joblisting.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adnan.joblisting.dto.LoginRequest;
import com.adnan.joblisting.dto.LoginResponse;
import com.adnan.joblisting.model.Employer;
import com.adnan.joblisting.repository.EmployerRepository;
import com.adnan.joblisting.security.JwtUtil;

@RestController
public class EmployerController {

	@Autowired
	EmployerRepository erepo;
	
	@PostMapping("/auth/register")
    public Employer register(@RequestBody Employer employer) {

        // check duplicate email
        if (erepo.findByEmail(employer.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        
        // hash password
        employer.setPassword(
            new BCryptPasswordEncoder().encode(employer.getPassword())
        );

        Employer saved = erepo.save(employer);

        // ❌ hide password before returning
        saved.setPassword(null);
        return saved;
    }
	
//	@PostMapping("/auth/login")
//	public LoginResponse login(@RequestBody LoginRequest request) {
//
//	    Employer employer = erepo.findByEmail(request.getEmail())
//	            .orElseThrow(() -> new RuntimeException("Invalid email"));
//
//	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//	    if (!encoder.matches(request.getPassword(), employer.getPassword())) {
//	        throw new RuntimeException("Invalid password");
//	    }
//
//	    // ✅ Generate JWT
//	    String token = JwtUtil.generateToken(employer.getId());
//
//	    return new LoginResponse(
//	            employer.getId(),
//	            employer.getCname(),
//	            employer.getEmail(),
//	            token
//	    );
//	}
	@PostMapping("/auth/login")
	public Map<String, Object> login(@RequestBody LoginRequest request) {

	    Employer employer = erepo.findByEmail(request.getEmail())
	            .orElseThrow(() -> new RuntimeException("Invalid email"));

	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    if (!encoder.matches(request.getPassword(), employer.getPassword())) {
	        throw new RuntimeException("Invalid password");
	    }

//	    String token = JwtUtil.generateToken(employer.getId());
	    String token = JwtUtil.generateToken(employer.getId());



	    employer.setPassword(null);

	    Map<String, Object> res = new HashMap<>();
	    res.put("employer", employer);
	    res.put("token", token);

	    return res;
	}



	@PostMapping("/auth/logout")
	public boolean logout() {
		return true;
	}
	@GetMapping("/all")
	public List<Employer> getAll() {
		List<Employer> employers = erepo.findAll();
		return employers;
	}
	

}
