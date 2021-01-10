package net.icestone.ols.security;

import static net.icestone.ols.security.SecurityConstants.SIGN_UP_URLS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.BeanIds;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.icestone.ols.service.OleUserDetailsService;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
    @Autowired
    private OleUserDetailsService oleUserDetailsService;
    
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {return  new JwtAuthenticationFilter();}
    
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
            .antMatchers(SIGN_UP_URLS).permitAll()
            .anyRequest().authenticated()
        .and()
        	.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        	.httpBasic().authenticationEntryPoint(unauthorizedHandler)
        ;
        
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}