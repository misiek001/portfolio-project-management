package com.misiek.dao;

import com.misiek.domain.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements AbstractDao<Employee> {

    @PersistenceContext
    SessionFactory sessionFactory;

    @Override
    public Optional<Employee> save(Employee project) {
        return Optional.empty();
    }

    @Override
    public List<Employee> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class );
        criteriaQuery.select(root);
        TypedQuery<Employee> allQuery = session.createQuery(criteriaQuery);
        return allQuery.getResultList();
    }

    @Override
    public Optional<Employee> find(Long id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);
        criteriaQuery.select(root).where(criteriaBuilder.gt(root.get("ID"),id));

        Query<Employee> query = session.createQuery(criteriaQuery);
        Employee result;
        try {
            result = query.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e){
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {

    }
}
