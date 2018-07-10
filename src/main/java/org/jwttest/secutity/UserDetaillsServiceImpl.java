package org.jwttest.secutity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.jwttest.domain.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserDetaillsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myuser = accountService.findUserByUsername(username);
        if(myuser == null) throw new UsernameNotFoundException(username);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        myuser.getRoles().forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        });

        return new User(myuser.getUsername(), myuser.getPassword(), authorities);
	}
    
}