package net.icestone.ols.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.icestone.ols.exceptions.UsernameAlreadyExistsException;
import net.icestone.ols.model.OleUser;
import net.icestone.ols.repository.OleUserRepository;


@Service
public class OleUserService {

    @Autowired
    private OleUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public OleUser saveUser (OleUser newUser){

        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //Username has to be unique (exception)
            //newUser.setUsername(newUser.getUsername());
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword
            return userRepository.save(newUser);

        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists");
        }
    	
    }
    
    public OleUser findByUsername(String username) {
    	return userRepository.findByUsername(username);
    }



}