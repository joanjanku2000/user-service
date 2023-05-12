package com.onlinecv.userservice.online_cv.model.entity;

import com.onlinecv.userservice.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static com.onlinecv.userservice.base.entity.BaseEntity.DELETE_CLAUSE;

@Entity
@Where(clause = DELETE_CLAUSE)
@SQLDelete(sql = "UPDATE UserData u SET u.deleted = true where u.id = ?")
public class UserData extends BaseEntity {
    @ManyToOne
    private AppUser user;
    @ManyToOne
    private UserDataKey userDataKey;

    private String keyValue;

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public UserDataKey getUserDataKey() {
        return userDataKey;
    }

    public void setUserDataKey(UserDataKey userDataKey) {
        this.userDataKey = userDataKey;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }
}
