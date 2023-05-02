package com.onlinecv.userservice.model.entity;

import com.onlinecv.userservice.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE UserDataKey u SET u.deleted = false where u.id = ?")
public class UserDataKey extends BaseEntity {
    private String keyName;
}
