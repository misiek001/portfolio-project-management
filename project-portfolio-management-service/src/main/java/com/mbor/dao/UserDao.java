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

    public Optional<User> findByUsername(String userName) {

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("employee").get("userName"), userName));
            TypedQuery<User> allQuery = entityManager.createQuery(criteriaQuery);
            return  Optional.ofNullable(allQuery.getSingleResult());

    }
}
