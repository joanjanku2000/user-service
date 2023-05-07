package com.onlinecv.userservice.base.repository.impl;

import com.onlinecv.userservice.base.entity.BaseEntity;
import com.onlinecv.userservice.base.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
public class BaseRepositoryImpl implements BaseRepository {
    private static final Logger log = LoggerFactory.getLogger(BaseRepositoryImpl.class);
    private static final String FIND_QUERY = "SELECT e from #ENTITY e where e.#COLUMN = ?1 ";
    private static final String COLUMN = "#COLUMN";
    private static final String ENTITY = "#ENTITY";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BaseEntity> findBy(String entity, String columnName, Object value) {
        return entityManager.createQuery(FIND_QUERY.replace(COLUMN, columnName).replace(ENTITY, entity), BaseEntity.class).setParameter(1, value).getResultList();
    }
}
