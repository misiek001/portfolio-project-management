package com.misiek.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
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
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        Optional<T> returnedEntity = Optional.empty();
        try {
            transaction.begin();
            session.persist(t);
            transaction.commit();
            returnedEntity = Optional.of(t);
        } catch (RuntimeException e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        return returnedEntity;
    }

    @Override
    public Optional<T> find(Long id) {

        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(clazz, id));
        }
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        Optional<T> entityToDelete = Optional.ofNullable(session.get(clazz, id));
        if (entityToDelete.isPresent()) {
            try {
                transaction.begin();
                session.delete(entityToDelete.get());
                transaction.commit();
            } catch (RuntimeException e){
                transaction.rollback();
            } finally {
                session.close();
            }
        }
        else {
            throw new NoResultException();
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
