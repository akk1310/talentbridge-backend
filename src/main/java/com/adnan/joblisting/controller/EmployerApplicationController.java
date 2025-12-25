package com.adnan.joblisting.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adnan.joblisting.model.Application;
import com.adnan.joblisting.repository.ApplicationRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/employer")
public class EmployerApplicationController {

    @Autowired
    private ApplicationRepository appRepo;

    @GetMapping("/applications")
    public List<Application> getApplications(HttpServletRequest request) {

        String employerId = (String) request.getAttribute("employerId");

        if (employerId == null) {
            throw new RuntimeException("Employer not authenticated");
        }

        return appRepo.findByEmployerId(employerId);
    }
}

