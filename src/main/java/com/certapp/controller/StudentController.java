package com.certapp.controller;

import com.certapp.model.Student;
import com.certapp.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class StudentController {

    @Autowired
    private CertificateService certificateService;

    @PostMapping("/generate")
    public String generate(@RequestBody Student student) {
        certificateService.generateAndSend(student);
        return "Certificate sent to " + student.getEmail();
    }
}
