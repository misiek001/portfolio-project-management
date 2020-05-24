package com.mbor.dao;

import com.mbor.domain.DemandSheet;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class DemandSheetDao extends RawDao<DemandSheet> implements IDemandSheetDao {

    public DemandSheetDao() {
        this.clazz = DemandSheet.class;
    }

    @Override
    public List<DemandSheet> getAllDemandSheetsOfBRMWithNoProject(Long brmId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DemandSheet> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<DemandSheet> demandSheet = criteriaQuery.from(DemandSheet.class);
        criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(demandSheet.get("businessRelationManager").get("id"), brmId), criteriaBuilder.isNull(demandSheet.get("project"))));
        TypedQuery<DemandSheet> demandSheetTypedQuery = entityManager.createQuery(criteriaQuery);
        return demandSheetTypedQuery.getResultList();
    }
}
