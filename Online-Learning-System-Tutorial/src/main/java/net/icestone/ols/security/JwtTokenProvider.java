package net.icestone.ols.security;

import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import net.icestone.ols.dao.OleUserDetails;
import net.icestone.ols.model.OleUser;
import net.icestone.ols.security.Token;
import net.icestone.ols.service.OleUserDetailsService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import static net.icestone.ols.security.SecurityConstants.EXPIRATION_TIME;
import static net.icestone.ols.security.SecurityConstants.REFRESH_TOKEN_EXPIRATION_TIME;
import static net.icestone.ols.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {

	
    @Autowired
    private OleUserDetailsService customUserDetailsService;
	
    //Generate token with Authentication
    public String generateToken(Authentication authentication){
    	
    	OleUserDetails oleUserDetails = (OleUserDetails)authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime()+EXPIRATION_TIME);

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        
        Map<String,Object> claims = new HashMap<>();
        claims.put("Username", oleUserDetails.getUsername());
        claims.put("authorities", authorities);

        return Jwts.builder()
                .setSubject(oleUserDetails.getUsername())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        }

    //Generate token with AppUser
    public String generateToken(OleUser oleUser){
    	
        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime()+EXPIRATION_TIME);

        String username = oleUser.getUsername() ;
        
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        
        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        

        Map<String,Object> claims = new HashMap<>();
        claims.put("Username", oleUser.getUsername());
        claims.put("authorities", authorities);

        return Jwts.builder()
                .setSubject(username)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        }
    
    public Token generateRefreshToken(String subject) {
        Date now = new Date();
        Long duration = REFRESH_TOKEN_EXPIRATION_TIME;
        Date expiryDate = new Date(now.getTime()+REFRESH_TOKEN_EXPIRATION_TIME);
        Map<String,Object> claims = new HashMap<>();
        claims.put("Username", subject);
        String token = Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        
        validateRefreshToken(token);
        
        return new Token(Token.TokenType.REFRESH, token, duration, LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
    }
    
    //Validate the token
    public boolean validateToken(String token, HttpServletRequest httpServletRequest){
   	
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            System.out.println("Invalid JWT Signature");
        }catch (MalformedJwtException ex){
            System.out.println("Invalid JWT Token");
        }catch (ExpiredJwtException ex){
            System.out.println("Expired JWT token");
            httpServletRequest.setAttribute("expired",ex.getMessage());
        }catch (UnsupportedJwtException ex){
            System.out.println("Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            System.out.println("JWT claims string is empty");
        }
        return false;
    }
    
    public boolean validateRefreshToken(String token) {
    	
        try {
            Jwts.parser().setSigningKey(SECRET).parse(token);
            return true;
        } catch (SignatureException ex) {
            ex.printStackTrace();
        } catch (MalformedJwtException ex) {
            ex.printStackTrace();
        } catch (ExpiredJwtException ex) {
            ex.printStackTrace();
        } catch (UnsupportedJwtException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        
        String username = (String)claims.get("Username");
        
        return username;
    }

    public LocalDateTime getExpiryDateFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        return LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
    }
    
}