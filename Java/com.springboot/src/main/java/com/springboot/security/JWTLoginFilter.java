package com.springboot.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * this class serves as the filter for the incoming login requests.
 * @author saurabhtiwari
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

  public JWTLoginFilter(String url, AuthenticationManager authManager) {
    super(new AntPathRequestMatcher(url));
    setAuthenticationManager(authManager);
  }

  /**
   * this method is automatically called as a part of Spring security filter because we 
   * have passed authentication manager along with JWTLoginFilter in WebSecurityConfig class.
   * 
   * As a result the spring calls the attemptAuthenticaton method from AbstractAuthenticationProcessingFilter
   * class. This method overrides that method hence this method is invoked.
   * 
   * This method calls the authenticate method from AuthenticationProvider interface. 
   * There is a default implemntation for it in spring security but usually we would like to override
   * it with our custom authentication logic as I have done in Custom Authentication provider class.
   */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
      throws AuthenticationException, IOException, ServletException {
    AccountCredentials creds = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);
    return getAuthenticationManager().authenticate(
        new UsernamePasswordAuthenticationToken(
            creds.getUsername(),
            creds.getPassword()
        )
    );
  }

  
  /**
   * the spring security framework automatically calls this method once the user has been
   * authenticated as per the logic of the authenticate method called in attemptAuthentication method above.
   * At this moment authentication would have been successful already and auth.authenticate should be true.
   * This method then calls our JWTAuthentication service to add a token to the out-going response.
   */
  @Override
  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
      Authentication auth) throws IOException, ServletException {
	  if(auth.isAuthenticated()) {
		  /**
		   * we can create a claims/authorities payload to be sent to client.
		   * Basically we can set here any data that we want to send to the client.
		   * Usually all the basic data like name, id, roles and permissions are sent here 
		   * so that you can fetch them in subsquent calls and need no more DB validation
		   */
		  JWTPayload payload = new JWTPayload();
		  List<String> roles = new ArrayList<String>();
		  // add the authorities (which have been assigned inside authenticate method) to payload
		  if(null != auth.getAuthorities() && auth.getAuthorities().size() > 0) {
			  for(GrantedAuthority authority : auth.getAuthorities()) {
				  roles.add(authority.getAuthority());
			  }
		  }
		  
		  payload.setUsername(auth.getName());
		  payload.setUserId("ST-1234");
		  payload.setRoles(roles);
		  
		  // can add any other data if required like permissions and stuff
		  List<String> permissions = new ArrayList<String>();
		  permissions.add("JiraScreen");
		  permissions.add("ConfluenceScreen");
		  permissions.add("GitScreen");
		  payload.setPermissions(permissions);
		  
		  JWTAuthenticationService.addAuthentication(res, payload);
	  }
  }
}
