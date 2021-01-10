package net.icestone.ols.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.icestone.ols.model.OleUser;
import net.icestone.ols.payloads.JWTLoginSucessReponse;
import net.icestone.ols.payloads.LoginRequest;
import net.icestone.ols.security.JwtTokenProvider;
import net.icestone.ols.security.Token;
import net.icestone.ols.service.MapValidationErrorService;
import net.icestone.ols.service.OleUserService;
import net.icestone.ols.util.CookieUtil;
import net.icestone.ols.util.SecurityCipher;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.Valid;

import static net.icestone.ols.security.SecurityConstants.TOKEN_PREFIX;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private OleUserService userService;
    
   
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private CookieUtil cookieUtil;
    
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(
    		@CookieValue(name = "refreshToken", required = false) String refreshToken,
    		@Valid @RequestBody LoginRequest loginRequest, BindingResult result ){
    	
    	Token newRefreshToken;
    	HttpHeaders responseHeaders = new HttpHeaders();
    	
        String username = loginRequest.getUsername();
    	
    	newRefreshToken = tokenProvider.generateRefreshToken(username);
    	
    	addRefreshTokenCookie(responseHeaders, newRefreshToken);
    	
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX +  tokenProvider.generateToken(authentication);

        return ResponseEntity.ok().headers(responseHeaders).body(new JWTLoginSucessReponse(true, jwt));
    }

    
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
    		@CookieValue(name = "refreshToken", required = false) String refreshToken,
    		@Valid @RequestBody LoginRequest loginRequest, BindingResult result ){
    	
    	String decryptedRefreshToken = SecurityCipher.decrypt(refreshToken);

    	Boolean refreshTokenValid = tokenProvider.validateRefreshToken(decryptedRefreshToken);
        
    	 if (!refreshTokenValid) {
             throw new IllegalArgumentException("Refresh Token is invalid!");
         }
    	 
    	 String currentUser = tokenProvider.getUsernameFromToken(decryptedRefreshToken);
    	 
    	 OleUser oleUser = userService.findByUsername(currentUser);
    	 
    	Token newRefreshToken;
    	HttpHeaders responseHeaders = new HttpHeaders();
    	
    	newRefreshToken = tokenProvider.generateRefreshToken(currentUser);
    	
    	addRefreshTokenCookie(responseHeaders, newRefreshToken);
    	
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        String jwt = TOKEN_PREFIX +  tokenProvider.generateToken(oleUser);

        return ResponseEntity.ok().headers(responseHeaders).body(new JWTLoginSucessReponse(true, jwt));
    }
    
    
    private void addRefreshTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createRefreshTokenCookie(token.getTokenValue(), token.getDuration()).toString());
    }
}