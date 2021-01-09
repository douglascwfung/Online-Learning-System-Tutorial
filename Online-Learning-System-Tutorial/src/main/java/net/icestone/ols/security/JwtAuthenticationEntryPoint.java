package net.icestone.ols.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import net.icestone.ols.apierror.ApiError;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException ex) throws IOException, ServletException {

    	HttpStatus status;
    	
        if (ex instanceof BadCredentialsException ) {
        	status = HttpStatus.UNAUTHORIZED;
        } else if (ex instanceof InsufficientAuthenticationException ) {
        	status = HttpStatus.UNAUTHORIZED;
        } else {
        	status = HttpStatus.BAD_REQUEST;
        }
        
        ApiError apiError = new ApiError(status);
        apiError.setMessage(ex.getMessage());
        
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(status.value());
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String jsonLoginResponse = mapper.writeValueAsString(apiError);
       
        httpServletResponse.getWriter().print(jsonLoginResponse);
        
    }


}
