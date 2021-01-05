package net.icestone.ols.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import net.icestone.ols.model.SchoolSubject;

@Component
public class SchoolSubjectValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return SchoolSubject.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

    	SchoolSubject schoolSubject = (SchoolSubject) object;

        if( schoolSubject.getSubjectName().length() <6){
            errors.rejectValue("subjectName","Length", "Subject Name must be at least 6 characters");
        }

    }
}