package net.icestone.ols.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "school_subject")
@Data
public class SchoolSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @NotBlank(message = "Subject name is required")
    @Column(name="subject_name", unique = true)
    private String subjectName;

    @Size (min=5, max=200)
    @Column(name="subject_description")
    private String subjectDesc;
}