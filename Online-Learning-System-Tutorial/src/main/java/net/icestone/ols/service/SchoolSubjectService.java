package net.icestone.ols.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.icestone.ols.model.SchoolSubject;
import net.icestone.ols.repository.SchoolSubjectRepository;

@Service
public class SchoolSubjectService {
	
	@Autowired
	private SchoolSubjectRepository schoolSubjectRepository;

	//Standard function provided by Repository

    public Iterable<SchoolSubject> findAllSchoolSubjects(){
        return schoolSubjectRepository.findAll();
    }
	
	public Optional<SchoolSubject> findById(Long id) {
		return schoolSubjectRepository.findById(id);
	};
	
    public SchoolSubject saveOrUpdateSchoolSubject(SchoolSubject schoolSubject){
            return schoolSubjectRepository.save(schoolSubject);
    }
	
    public void deleteSchoolSubjectById(Long id) {
    	schoolSubjectRepository.deleteById(id);
    }
    
	//Customized function
	
	public SchoolSubject findBySubjectName(String subjectName) {
		return schoolSubjectRepository.findBySubjectName(subjectName);
	}
	
}
