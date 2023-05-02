package com.onlinecv.userservice.model.entity;

import com.onlinecv.userservice.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE UserRole u SET u.deleted = false where u.id = ?")
public class UserRole extends BaseEntity {
    @ManyToOne
    private User user;
    @ManyToOne
    private Role role;
}
