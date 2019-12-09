package com.misiek.dao;

import com.misiek.domain.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class ProjectDaoImpl implements AbstractDao<Project> {

    @Autowired
    private SessionFactory sessionFactory;

    public Optional<Project> save(Project project) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        Optional<Project> returnedProject = Optional.empty();
        try {
            transaction.begin();
            session.persist(project);
            transaction.commit();
            returnedProject = Optional.of(project);
        } catch (RuntimeException e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        return returnedProject;
    }

    public Optional<Project> find(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Project.class, id));
        }
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        Optional<Project> projectToDelete = Optional.ofNullable(session.get(Project.class, id));
        if (projectToDelete.isPresent()) {
            try {
                transaction.begin();
                session.delete(projectToDelete.get());
                transaction.commit();
            } catch (RuntimeException e){
                transaction.rollback();
            } finally {
                session.close();
            }
        }
}

    @Override
    public List<Project> findAll() {
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
            Root<Project> root = criteriaQuery.from(Project.class);
            criteriaQuery.select(root);
            TypedQuery<Project> allQuery = session.createQuery(criteriaQuery);
            List<Project> results = allQuery.getResultList();
            session.close();
            return results;
        }
    }
}
