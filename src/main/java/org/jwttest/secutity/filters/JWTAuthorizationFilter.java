package org.jwttest.secutity.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jwttest.secutity.SecurityConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


public class JWTAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
                final String jwt = request.getHeader(SecurityConstants.HEADER_STRING);
                if(jwt == null || !jwt.startsWith(SecurityConstants.TOKEN_PREFIX)) {
                    filterChain.doFilter(request, response);
                    return;
                }
                final Claims claims = Jwts.parser()
               .setSigningKey(SecurityConstants.SECRET)
               .parseClaimsJws(jwt.replace(SecurityConstants.TOKEN_PREFIX, ""))
               .getBody();
        
               final String username= claims.getSubject();
               final ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");

               Collection<GrantedAuthority> authoritties = new ArrayList<>();

               roles.forEach(r-> {
                   authoritties.add(new SimpleGrantedAuthority(r.get("authority")));
               });

               UsernamePasswordAuthenticationToken authenticationUser = 
               new UsernamePasswordAuthenticationToken(username, null, authoritties);
               SecurityContextHolder.getContext().setAuthentication(authenticationUser);
               filterChain.doFilter(request, response);

	}

}