package net.icestone.ols.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "school_subject")
public class SchoolSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @Column(name="subject_name")
    private String subjectName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public SchoolSubject(Long id, String subjectName) {
		this.id = id;
		this.subjectName = subjectName;
	}

	public SchoolSubject() {
	}

	@Override
	public String toString() {
		return "SchoolSubject [id=" + id + ", subjectName=" + subjectName + "]";
	}
    
}