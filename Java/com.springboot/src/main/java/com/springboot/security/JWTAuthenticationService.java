package com.springboot.security;

import static java.util.Collections.emptyList;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * this class is responsible for creating a new JWT token once the user has been 
 * authenticated. Also this class is responsible for fetching and checking the
 * JWT token from subsquent incoming request.
 * 
 * JWT token consist of 3 sections:
 * 
 * 1. First section carries few reserved keywords which explain various meta data about
 * the token, like algo used, intended audition, origin of token, expiration time etc.
 * 
 * 2. The second section is very important as it contains various data regarding authorization.
 * These are also called as claims. These tells which section of the application can be accessed 
 * using this token. 
 * 
 * 3. This also leads to a major drawback in JWT implemntation. If the authorization 
 * is revoked from user in backend, the client will still continue to work with the same privileges
 * that were passed in the last JWT token.
 * 
 * 4. The last section of the token consist of a secret key (any secret key that we want)
 * that will be used to sign or encrypt the token.
 * 
 * 5. Another issue with using JWT is that the token tends to become huge withincrease in the payload.
 * 
 * This token is passed along the response header under Authorization header. For example if the 
 * token is 1234. The authorization header will look like - Authorization: Bearer 1234
 * @author saurabhtiwari
 */
public class JWTAuthenticationService {
	
  static final long EXPIRATIONTIME = 300; // 5 mins
  static final String SECRET_KEY = "My$eCrEtkeY4EnCryptIoNOfJWT";
  static final String TOKEN_PREFIX = "Bearer";
  static final String HEADER_STRING = "Authorization";

  /**
   * this method adds the token to the header.
   * this method is called from the JWTLoginfilter which is
   * used against the incoming login call.
   * @param res
   * @param username
   */
  static void addAuthentication(HttpServletResponse res, JWTPayload payload) {
	// all authentication related data like authorities and permissions can be 
	// embed to the token in a map using setClaims()
	Map<String, Object> claims = new HashMap<String, Object>();
	claims.put("roles", payload.getRoles());
	claims.put("permissions", payload.getPermissions());
    String JWT = Jwts.builder()
        .setSubject(payload.getUsername())
        .setClaims(claims)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
    res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
  }

  
  /**
   * this method retrives the token from the header and validates it.
   * this method is called from the JWTAuthentication filter which is
   * used against all the incoming calls except the login.
   * @param request
   * @return
   */
  static Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);
    if (token != null) {
      // parse the token.
      String user = Jwts.parser()
          .setSigningKey(SECRET_KEY)
          .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
          .getBody()
          .getSubject();

      return user != null ?
          new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
          null;
    }
    return null;
  }
}