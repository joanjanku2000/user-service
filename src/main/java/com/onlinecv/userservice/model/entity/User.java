package com.onlinecv.userservice.model.entity;

import com.onlinecv.userservice.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE User u SET u.deleted = false where u.id = ?")
public class User extends BaseEntity {
    private String username;
    private String name;
    private String lastName;
    private LocalDate birthday;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles;
}
