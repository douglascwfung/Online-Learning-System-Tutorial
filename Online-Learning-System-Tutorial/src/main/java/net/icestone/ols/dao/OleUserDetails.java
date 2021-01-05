package net.icestone.ols.dao;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import net.icestone.ols.model.OleRole;
import net.icestone.ols.model.OleUser;
 
public class OleUserDetails implements UserDetails {
 
    private OleUser oleUser;
     
    public OleUserDetails(OleUser oleUser) {
        this.oleUser = oleUser;
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<OleRole> roles = oleUser.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
         
        for (OleRole oleRole : roles) {
            authorities.add(new SimpleGrantedAuthority(oleRole.getName()));
        }
         
        return authorities;
    }
 
    @Override
    public String getPassword() {
        return oleUser.getPassword();
    }
 
    @Override
    public String getUsername() {
        return oleUser.getUsername();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return oleUser.isEnabled();
    }
 
}