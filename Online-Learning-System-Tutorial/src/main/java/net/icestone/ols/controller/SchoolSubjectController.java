package net.icestone.ols.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.icestone.ols.exceptions.CustomConstraintViolationException;
import net.icestone.ols.model.SchoolSubject;
import net.icestone.ols.service.SchoolSubjectService;
import net.icestone.ols.validator.SchoolSubjectValidator;

@RestController
@RequestMapping("/api/schoolsubject")
public class SchoolSubjectController {

	
	@Autowired
	private SchoolSubjectService schoolSubjectService;

    @Autowired
    private SchoolSubjectValidator schoolSubjectValidator;
	
	
    @GetMapping("/get/all")
    public Iterable<SchoolSubject> getAllProjects(){return schoolSubjectService.findAllSchoolSubjects();}

    @GetMapping("/get/subjectname/{subjectName}")
    public ResponseEntity<?> findBySubjectName(@PathVariable String subjectName) {
    	
    	SchoolSubject schoolSubject = schoolSubjectService.findBySubjectName(subjectName);
    	
//    	if (schoolSubject == null ) {
//    		System.out.println("Null");
//    	} else {
//    		System.out.println("Not null");
//    	}
    	
    	return new ResponseEntity<SchoolSubject> (schoolSubject, HttpStatus.OK);
    	
    }
	
    
    @GetMapping("/get/id/{id}")
    public ResponseEntity<?> findByID(@PathVariable Long id) {
    	
    	Optional<SchoolSubject> schoolSubject = schoolSubjectService.findById(id);
    	
    	if (schoolSubject == null ) {
    		System.out.println("Null");
    	} else {
    		System.out.println("Not null");
    	}
    	
   	
        //return schoolSubject.map(c -> ResponseEntity.ok().body(c))
        //        .orElse(ResponseEntity.notFound().build());
    	
//        return schoolSubject.map(c -> new ResponseEntity<SchoolSubject> (c, HttpStatus.OK))
//                .orElse(ResponseEntity.notFound().build());

        
        if (schoolSubject.isPresent()) {
        	  return ResponseEntity.ok().body(schoolSubject.get());
        	 }
        	 return ResponseEntity.notFound().build();
        	}
    
    
    @PostMapping("/post")
    public ResponseEntity<?> createSchoolSubject(@Valid @RequestBody SchoolSubject schoolSubject, BindingResult result){
    	
    	schoolSubjectValidator.validate(schoolSubject,result);
    	
    	if (result.getErrorCount() > 0) throw new CustomConstraintViolationException("School Subject Validation Error", result) ; 
    	
    	SchoolSubject schoolSubject1 = schoolSubjectService.saveOrUpdateSchoolSubject(schoolSubject);
        return new ResponseEntity<SchoolSubject>(schoolSubject1, HttpStatus.CREATED);
    }
    
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id){
    	
    	schoolSubjectService.deleteSchoolSubjectById(id);
    	
        return new ResponseEntity<String>("Project with ID: '"+id+"' was deleted", HttpStatus.OK);
    }
    

}
