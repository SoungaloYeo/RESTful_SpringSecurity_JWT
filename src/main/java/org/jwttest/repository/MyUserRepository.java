package org.jwttest.repository;

import org.jwttest.domain.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    public MyUser findByUsername(String username);

}