package com.onlinecv.userservice.online_cv.repository;

import com.onlinecv.userservice.online_cv.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("select ur from UserRole ur where ur.user.id = :id and ur.deleted = false ")
    List<UserRole> findAllByUserIdAndDeletedFalse(@Param("id") Long id);
}
