package com.mbor.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public abstract class RawDao<T> implements IDao<T> {

    protected Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Optional<T> save(T t) {
        entityManager.persist(t);
        return Optional.ofNullable(t);
    }

    @Override
    public Optional<T> find(Long id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.getReference(clazz, id));
    }

    @Override
    public List<T> findAll() {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
            Root<T> root = criteriaQuery.from(clazz);
            criteriaQuery.select(root);
            TypedQuery<T> allQuery = entityManager.createQuery(criteriaQuery);
            return allQuery.getResultList();
        }

    @Override
    public Optional<T> update(T t) {
       return Optional.ofNullable(entityManager.merge(t));
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

}
