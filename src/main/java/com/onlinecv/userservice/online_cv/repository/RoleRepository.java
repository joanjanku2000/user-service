package com.onlinecv.userservice.online_cv.repository;

import com.onlinecv.userservice.online_cv.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
