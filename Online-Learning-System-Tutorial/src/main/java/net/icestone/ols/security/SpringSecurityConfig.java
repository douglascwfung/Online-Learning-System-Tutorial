package net.icestone.ols.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.BeanIds;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.icestone.ols.service.OleUserDetailsService;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
    @Autowired
    private OleUserDetailsService oleUserDetailsService;
    
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(oleUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
    
    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception { 
        http
        .cors()
        .and()
        	.csrf().disable()
        	.authorizeRequests()
            .antMatchers("/api/schoolsubject/get/**").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
            .antMatchers("/api/schoolsubject/post/**").hasAnyAuthority("ADMIN", "CREATOR", "EDITOR")
            .antMatchers("/api/schoolsubject/delete/**").hasAuthority("ADMIN")
            .anyRequest().authenticated()
        .and()
        	.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
        .and()
        	.httpBasic().authenticationEntryPoint(unauthorizedHandler)
        
        //.httpBasic().and()
        //.formLogin().permitAll().and()
         
        ;
    }
}