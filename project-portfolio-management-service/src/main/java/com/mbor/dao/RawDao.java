package com.mbor.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public abstract class RawDao<T> implements IDao<T> {

    protected Class<T> clazz;

    protected SessionFactory sessionFactory;

    @Autowired
    public RawDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<T> save(T t) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            try {
                transaction.begin();
                session.save(t);
                transaction.commit();
                return Optional.ofNullable(t);
            } catch (RuntimeException e){
                e.printStackTrace();
                transaction.rollback();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> find(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(clazz, id));
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Optional<T> projectToDelete = find(id);
            session.delete(projectToDelete.get());
        }
    }

    @Override
    public List<T> findAll() {
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
            Root<T> root = criteriaQuery.from(clazz);
            criteriaQuery.select(root);
            TypedQuery<T> allQuery = session.createQuery(criteriaQuery);
            return allQuery.getResultList();
        }
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

}
