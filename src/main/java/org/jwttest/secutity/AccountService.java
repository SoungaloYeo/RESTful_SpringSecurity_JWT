package org.jwttest.secutity;

import org.jwttest.domain.MyRole;
import org.jwttest.domain.MyUser;

public interface AccountService{

    public MyUser saveUser(MyUser user);

    public MyRole saveRole(MyRole role);

    public void addRoleToUser(String username, String roleName);
    
    public MyUser findUserByUsername(String username);
}