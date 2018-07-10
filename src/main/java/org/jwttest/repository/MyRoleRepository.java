package org.jwttest.repository;

import org.jwttest.domain.MyRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyRoleRepository extends JpaRepository<MyRole, Long> {

    public MyRole findByRoleName(String roleName);

}