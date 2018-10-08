package com.springboot.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * this class implements the authenticate method from AuthenticateProvider interface,
 * thus providing Spring with our custom logic for authenticating the user.
 * @author saurabhtiwari
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	/**
	 * the below authenticate method must provide an Auth object token once it has
	 * successfully authenticated the user. In case, it fails to authenticate the user
	 * it can return null. This means the request will be passed to next filter in line.
	 */
	@Override
	public Authentication authenticate(Authentication authentication ) throws AuthenticationException {
	    String userName = authentication.getName().trim();
        String password = authentication.getCredentials().toString().trim();
        Authentication auth = null;
        // Authenticate the user based on your custom logic. This is the place
        // where you can validate user against a DB or some external service.
        if(userName.length() <= 4 && password.length() >= 5) {
        	// if required you can set the authorities (roles and other data for user from DB), this data will
        	// then be availbale in auth object and can be passed to JWT token creation. You can 
        	// also choose to not add any authorities here and add directly while token creation.
        	// Ideally you should assign authorities in this method only and use them later.
        	// Depending on the user validation you can decide here which authorities to assign to this user.
        	
        	// After this method returns the successfulAuthentication method from JWTLoginFilter
        	// is called automatically. Depending on whether or not you have granted authorities
        	// in this method the auth.isAuthenticated is set to true or false in successfulAuthentication
        	// method.
        	
	         Collection<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
	         grantedAuths.add(new SimpleGrantedAuthority("ADMIN"));
	         grantedAuths.add(new SimpleGrantedAuthority("CUSTOMER"));
	         auth = new UsernamePasswordAuthenticationToken(userName, password, grantedAuths);
        	
	        //auth = new UsernamePasswordAuthenticationToken(userName, password);
        }
        return auth;
	}
	
	/**
	 * this method is important because it tells the spring security that which
	 * class of input Authentication this provider is capable of processing.
	 * @param authentication
	 * @return
	 */
    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
    
}
