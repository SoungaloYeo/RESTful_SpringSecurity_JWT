package org.jwttest.secutity;

import org.jwttest.domain.MyRole;
import org.jwttest.domain.MyUser;
import org.jwttest.repository.MyRoleRepository;
import org.jwttest.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
	private MyUserRepository myUserRepository; 
	
    @Autowired
    private MyRoleRepository myRoleRepository; 

	@Override
	public MyUser saveUser(MyUser myuser) {
        String hashPwd = bCryptPasswordEncoder.encode(myuser.getPassword());
		myuser.setPassword(hashPwd);
		// System.out.println("Password crypté :"+user.getPassword());
		return myUserRepository.save(myuser);
	}

	@Override
	public MyRole saveRole(MyRole myrole) {
		return myRoleRepository.save(myrole);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
        MyUser myuser = myUserRepository.findByUsername(username);
        MyRole myrole = myRoleRepository.findByRoleName(roleName);
        
        myuser.getRoles().add(myrole);
	}

	@Override
	public MyUser findUserByUsername(String username) {
		return myUserRepository.findByUsername(username);
	}

}