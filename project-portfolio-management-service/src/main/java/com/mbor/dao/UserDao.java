package com.mbor.dao;

import com.mbor.domain.security.User;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class UserDao extends RawDao<User> implements IUserDao{

    public UserDao() {
        this.clazz = User.class;
    }

    public Optional<User> findByUsername(String userName) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(clazz);
            Root<User> root = criteriaQuery.from(clazz);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("employee").get("userName"), userName));
            TypedQuery<User> allQuery = entityManager.createQuery(criteriaQuery);
            return  Optional.ofNullable(allQuery.getSingleResult());
    }
}
