package org.jwttest.secutity.filters;

import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;

import java.sql.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jwttest.domain.MyUser;
import org.jwttest.secutity.SecurityConstants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter (AuthenticationManager authenticationManager) {
        super();
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
                MyUser myUser = null;
                try {
					myUser = new ObjectMapper().readValue(request.getInputStream(), MyUser.class);
				} catch (Exception e) {
					throw new RuntimeException(e);
                }
                System.out.println("*******************");
                System.out.println("username :"+myUser.getUsername());
                System.out.println("password :"+myUser.getPassword());
                System.out.println("*******************");
        return authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(myUser.getUsername(), myUser.getPassword()));
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse 
        response, FilterChain chain,Authentication authResult) throws IOException, ServletException {
        
            User springUser = (User) authResult.getPrincipal();
            String jwt = Jwts.builder()
                              .setSubject(springUser.getUsername())
                              .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
                              .signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET)
                              .claim("roles", springUser.getAuthorities())
                              .compact();
            response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+jwt);
            // super.successfulAuthentication(request, response, chain, authResult);
    }

}