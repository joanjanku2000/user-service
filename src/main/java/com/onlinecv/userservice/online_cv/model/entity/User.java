package com.onlinecv.userservice.online_cv.model.entity;

import com.onlinecv.userservice.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;

import static com.onlinecv.userservice.base.entity.BaseEntity.DELETE_CLAUSE;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Where(clause = DELETE_CLAUSE)
@SQLDelete(sql = "UPDATE User u SET u.deleted = true where u.id = ?")
public class User extends BaseEntity {
    private String username;
    private String name;
    private String lastName;
    private LocalDate birthday;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles;
}
