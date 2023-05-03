package com.onlinecv.userservice.model.entity;

import com.onlinecv.userservice.base.entity.BaseEntity;
import com.onlinecv.userservice.model.dto.UserDataKeyDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static com.onlinecv.userservice.base.entity.BaseEntity.DELETE_CLAUSE;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Where(clause = DELETE_CLAUSE)
@SQLDelete(sql = "UPDATE UserData u SET u.deleted = false where u.id = ?")
public class UserData extends BaseEntity {
    @ManyToOne
    private User user;
    @ManyToOne
    private UserDataKey userDataKey;
}
