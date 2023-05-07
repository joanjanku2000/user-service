package com.onlinecv.userservice.online_cv.repository;

import com.onlinecv.userservice.online_cv.model.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUserName(String userName);

    @Query("select case when count(u) > 0 then TRUE else FALSE end from AppUser u where u.userName = :username or u.email = :email and u.deleted = false and u.id <> :id")
    Boolean existsAppUsersByEmailOrUserNameButIsNotUserWithId(@Param("email") String email, @Param("username") String username, @Param("id") Long id);
}
