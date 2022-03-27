package com.app.epothon.jwt.repository;


import com.app.epothon.jwt.model.Role;
import com.app.epothon.jwt.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName role);
}