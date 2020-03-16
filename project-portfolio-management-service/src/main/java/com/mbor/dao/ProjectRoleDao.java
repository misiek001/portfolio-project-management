package com.mbor.dao;

import com.mbor.domain.employeeinproject.ProjectRole;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProjectRoleDao extends RawDao<ProjectRole> implements IProjectRoleDao {

    public ProjectRoleDao() {
        this.clazz = ProjectRole.class;
    }

    @Override
    public List<ProjectRole> findAllRoleOfEmployee(Long employeeId){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProjectRole> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<ProjectRole> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("employee").get("id"), employeeId));
        TypedQuery<ProjectRole> allQuery = entityManager.createQuery(criteriaQuery);
        return allQuery.getResultList();
    }

}
