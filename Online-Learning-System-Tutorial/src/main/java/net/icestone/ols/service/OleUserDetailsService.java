package net.icestone.ols.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import net.icestone.ols.dao.OleUserDetails;
import net.icestone.ols.model.OleUser;
import net.icestone.ols.repository.OleUserRepository;
 
@Service
public class OleUserDetailsService implements UserDetailsService {
 
    @Autowired
    private OleUserRepository oleUserRepository;
     
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        OleUser oleUser = oleUserRepository.findByUsername(username);
         
        if (oleUser == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new OleUserDetails(oleUser);
    }
 
}