package com.mbor.dao;

import com.mbor.domain.ProjectRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProjectRequestDao extends RawDao<ProjectRequest> implements IProjectRequestDao {

    public ProjectRequestDao() {
        this.clazz = ProjectRequest.class;
    }

    @Override
    public List<ProjectRequest> getAllProjectRequestsOfBRMWithNoProject(Long brmId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProjectRequest> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<ProjectRequest> ProjectRequest = criteriaQuery.from(ProjectRequest.class);
        criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(ProjectRequest.get("businessRelationManager").get("id"), brmId), criteriaBuilder.isNull(ProjectRequest.get("project"))));
        TypedQuery<ProjectRequest> ProjectRequestTypedQuery = entityManager.createQuery(criteriaQuery);
        return ProjectRequestTypedQuery.getResultList();
    }
}
