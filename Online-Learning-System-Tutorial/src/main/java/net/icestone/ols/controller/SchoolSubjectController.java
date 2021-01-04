package net.icestone.ols.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.icestone.ols.model.SchoolSubject;
import net.icestone.ols.repository.SchoolSubjectRepository;

public class SchoolSubjectController {
}

//@RestController
//@RequestMapping("/api/schoolsubject")
//public class SchoolSubjectController {
//
//	
//    @Autowired
//    private SchoolSubjectRepository schoolSubjectRepository;
//
//	
//    @GetMapping("subjectname/{subjectName}")
//    public ResponseEntity<?> findBySubjectName(@PathVariable String subjectName) {
//    	
//    	SchoolSubject schoolSubject = schoolSubjectRepository.findBySubjectName(subjectName);
//    	
//    	if (schoolSubject == null ) {
//    		System.out.println("Null");
//    	} else {
//    		System.out.println("Not null");
//    	}
//    	
//    	return new ResponseEntity<SchoolSubject> (schoolSubject, HttpStatus.OK);
//    	
//    }
//	
//    
//    @GetMapping("id/{id}")
//    public ResponseEntity<?> findByID(@PathVariable Long id) {
//    	
//    	Optional<SchoolSubject> schoolSubject = schoolSubjectRepository.findById(id);
//    	
//    	if (schoolSubject == null ) {
//    		System.out.println("Null");
//    	} else {
//    		System.out.println("Not null");
//    	}
//    	
//   	
//        //return schoolSubject.map(c -> ResponseEntity.ok().body(c))
//        //        .orElse(ResponseEntity.notFound().build());
//    	
////        return schoolSubject.map(c -> new ResponseEntity<SchoolSubject> (c, HttpStatus.OK))
////                .orElse(ResponseEntity.notFound().build());
//
//        
//        if (schoolSubject.isPresent()) {
//        	  return ResponseEntity.ok().body(schoolSubject.get());
//        	 }
//        	 return ResponseEntity.notFound().build();
//        	}
//        
//    
//    
//
//}
