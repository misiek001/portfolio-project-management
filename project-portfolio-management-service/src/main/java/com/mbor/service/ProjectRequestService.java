package com.mbor.service;

import com.mbor.dao.IDao;
import com.mbor.dao.IProjectRequestDao;
import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.BusinessUnit;
import com.mbor.domain.ProjectRequest;
import com.mbor.exception.NoBRMAssignedToBusinessUnitException;
import com.mbor.mapper.project.ProjectRequestMapper;
import com.mbor.model.ProjectRequestDTO;
import com.mbor.model.creation.ProjectRequestCreationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectRequestService extends RawService<ProjectRequest> implements IProjectRequestService {

    private final IProjectRequestDao ProjectRequestDao;

    private final IInternalBusinessUnitService businessUnitService;

    private final ProjectRequestMapper projectRequestMapper;

    @Autowired
    public ProjectRequestService(IProjectRequestDao ProjectRequestDao, IInternalBusinessUnitService businessUnitService, ProjectRequestMapper projectRequestMapper) {
        this.ProjectRequestDao = ProjectRequestDao;
        this.businessUnitService = businessUnitService;
        this.projectRequestMapper = projectRequestMapper;
    }

    @Override
    public com.mbor.model.creation.ProjectRequestDTO save(ProjectRequestCreationDTO projectRequestCreationDTO) {
        ProjectRequest projectRequest = projectRequestMapper.convertCreationDtoToEntity(projectRequestCreationDTO);
        BusinessUnit businessUnit = businessUnitService.findInternal(projectRequestCreationDTO.getBusinessUnitId());
        projectRequest.setBusinessUnit(businessUnit);
        BusinessRelationManager businessRelationManager = businessUnit.getBusinessRelationManager();
        if(businessRelationManager == null){
            throw new NoBRMAssignedToBusinessUnitException("No BRM assigned to this Business Unit");
        }
        projectRequest.setBusinessRelationManager(businessRelationManager);
        saveInternal(projectRequest);

        return projectRequestMapper.convertEntityToCreatedDto(projectRequest);
    }

    @Override
    public List<ProjectRequestDTO> findAll() {
        return null;
    }

    @Override
    public ProjectRequestDTO find(Long id) {
        return null;
    }

    @Override
    public List<ProjectRequestDTO> findAllProjectRequestsOfBRMWithNoProject(Long brmId){
        List<ProjectRequestDTO> result = new ArrayList<>();
        ProjectRequestDao.getAllProjectRequestsOfBRMWithNoProject(brmId).forEach(ProjectRequest -> result.add(projectRequestMapper.convertEntityToDto(ProjectRequest)));
        return result;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public IDao getDao() {
        return ProjectRequestDao;
    }
}
