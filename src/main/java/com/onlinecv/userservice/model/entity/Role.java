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
@SQLDelete(sql = "UPDATE Role r SET r.deleted = false where r.id = ?")
public class Role extends BaseEntity {
    private String name;
    private String description;
}
