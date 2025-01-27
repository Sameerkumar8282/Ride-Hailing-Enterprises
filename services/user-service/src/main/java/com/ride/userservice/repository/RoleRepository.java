package com.ride.userservice.repository;

import com.ride.userservice.model.Role;
import com.ride.userservice.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,String> {
    Optional<Role> findByUserRole(UserRole userRole);
}
