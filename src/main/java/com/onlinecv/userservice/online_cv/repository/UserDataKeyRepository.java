package com.onlinecv.userservice.online_cv.repository;

import com.onlinecv.userservice.online_cv.model.entity.UserDataKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataKeyRepository extends JpaRepository<UserDataKey,Long> {
}
