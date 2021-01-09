package net.icestone.ols.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
	
    @GetMapping("/math")
    public String getASubject(){
    	return "Math";
    	
    };
}

