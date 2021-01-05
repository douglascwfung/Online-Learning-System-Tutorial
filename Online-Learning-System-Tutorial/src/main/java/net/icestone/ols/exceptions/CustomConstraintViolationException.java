package net.icestone.ols.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomConstraintViolationException extends RuntimeException {
	
	private BindingResult bindingResult;
	
    public CustomConstraintViolationException(String message) {
        super(message);
    }
     
    public CustomConstraintViolationException(String message, BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }
    
}