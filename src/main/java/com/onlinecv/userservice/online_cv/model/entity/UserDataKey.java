package com.onlinecv.userservice.online_cv.model.entity;

import com.onlinecv.userservice.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static com.onlinecv.userservice.base.entity.BaseEntity.DELETE_CLAUSE;

@Entity
@Where(clause = DELETE_CLAUSE)
@SQLDelete(sql = "UPDATE user_data_key u SET u.deleted = true where u.id = ?")
public class UserDataKey extends BaseEntity {
    private String keyName;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
