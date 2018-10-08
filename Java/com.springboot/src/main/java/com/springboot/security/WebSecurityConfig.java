package com.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This is the most important class. This is where you start. Using the 
 * Enable Security annotation tells spring to do a lot of security 
 * related settings automatically. 
 * We need to override the following two versions of configure method
 * and provide our own settings there.
 * @author saurabhtiwari
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
  // this is the autowired instance of the class which provides a 
  //authentication logic for us.
  @Autowired
  CustomAuthenticationProvider customAuthProvider; 
	
  /**
   * we provide which URLs to permit to all and which URLs are to be filtered.
   * For Login URL we use a filter which checks for authentictaion. Once authenticated
   * a JWT token will be generated and returned to client.
   * For any other URL, we use a different filter which looks for previously
   * generated JWT token in the request.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers(HttpMethod.GET, "/api/v1/login/").permitAll()
        .anyRequest().authenticated()
        .and()
        // We filter the api/login requests
        .addFilterBefore(new JWTLoginFilter("/api/v1/login/", authenticationManager()),
                UsernamePasswordAuthenticationFilter.class)
        // And filter other requests to check the presence of JWT in header
        .addFilterBefore(new JWTAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);
  }

  /**
   * We can configure our authentication type by overriding the below version of 
   * configure method. We can provide more than one authentication as shown below.
   * These authentication are then called in the same manner, i.e. if the first one
   * returns null the second is called and so on. After all providers here are
   * called, I believe Spring also calls its default provider.
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	  
	auth.authenticationProvider(customAuthProvider);
    // Create a default account
    auth.inMemoryAuthentication()
    .passwordEncoder(passwordEncoder())
    .withUser("admin").password(passwordEncoder().encode("password")).roles("ADMIN");
    
    auth.inMemoryAuthentication()
    .passwordEncoder(passwordEncoder())
    .withUser("user").password(passwordEncoder().encode("password123")).roles("CUSTOMER");
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
}