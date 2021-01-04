package net.icestone.ols.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.icestone.ols.model.SchoolSubject;

//@Repository
////public interface SchoolSubjectRepository extends JpaRepository<SchoolSubject, Long>{
//public interface SchoolSubjectRepository extends CrudRepository<SchoolSubject, Long>{
//	
//	SchoolSubject findBySubjectName(String subjectName);
//		
//}


public interface SchoolSubjectRepository extends JpaRepository<SchoolSubject, Long> {
	
	
}
