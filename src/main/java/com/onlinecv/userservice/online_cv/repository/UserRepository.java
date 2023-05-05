package com.onlinecv.userservice.online_cv.repository;

import com.onlinecv.userservice.online_cv.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
