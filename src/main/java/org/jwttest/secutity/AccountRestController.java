package org.jwttest.secutity;

import org.jwttest.domain.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public MyUser register(@RequestBody RegisterForm registerForm) {

        if(!registerForm.getPassword().equals(registerForm.getRepassword()))
            throw new RuntimeException("confirme the password");

        MyUser user = accountService.findUserByUsername(registerForm.getUsername());
        if(user != null ) throw new RuntimeException("this user already existe");  

        MyUser myUser = new MyUser();
        myUser.setUsername(registerForm.getUsername());
        myUser.setPassword(registerForm.getPassword());
        accountService.saveUser(myUser);
        accountService.addRoleToUser(registerForm.getUsername(), "USER");
        return myUser;    
    }
}