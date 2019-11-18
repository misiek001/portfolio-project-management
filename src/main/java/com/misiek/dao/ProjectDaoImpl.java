package com.misiek.dao;

import com.misiek.domain.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ProjectDaoImpl implements ProjectDao {

    @PersistenceContext
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
            transaction.rollback();
        } finally {
            session.close();
        }
        return returnedProject;
    }

    public Optional<Project> find(Long id) {
        Session session = sessionFactory.openSession();
        return Optional.ofNullable(session.get(Project.class, id));
    }
}
