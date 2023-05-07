package com.onlinecv.userservice.online_cv.model.entity;

import com.onlinecv.userservice.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static com.onlinecv.userservice.base.entity.BaseEntity.DELETE_CLAUSE;

@Entity
@Where(clause = DELETE_CLAUSE)
@SQLDelete(sql = "UPDATE Role r SET r.deleted = true where r.id = ?")
public class Role extends BaseEntity {
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Role{" + "name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }
}
