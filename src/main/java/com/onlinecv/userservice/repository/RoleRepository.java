package com.onlinecv.userservice.repository;

import com.onlinecv.userservice.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
