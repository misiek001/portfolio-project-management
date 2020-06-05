package com.mbor.service;

import com.mbor.dao.IDao;
import com.mbor.dao.IDemandSheetDao;
import com.mbor.domain.BusinessRelationManager;
import com.mbor.domain.BusinessUnit;
import com.mbor.domain.DemandSheet;
import com.mbor.exception.NoBRMAssignedToBusinessUnitException;
import com.mbor.mapper.DemandSheetMapper;
import com.mbor.model.DemandSheetDTO;
import com.mbor.model.creation.DemandSheetCreatedDTO;
import com.mbor.model.creation.DemandSheetCreationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemandSheetService extends RawService<DemandSheet> implements IDemandSheetService {

    private final IDemandSheetDao demandSheetDao;

    private final IInternalBusinessUnitService businessUnitService;

    private final DemandSheetMapper demandSheetMapper;

    @Autowired
    public DemandSheetService(IDemandSheetDao demandSheetDao, IInternalBusinessUnitService businessUnitService, DemandSheetMapper demandSheetMapper) {
        this.demandSheetDao = demandSheetDao;
        this.businessUnitService = businessUnitService;
        this.demandSheetMapper = demandSheetMapper;
    }

    @Override
    public DemandSheetCreatedDTO save(DemandSheetCreationDTO demandSheetCreationDTO) {
        DemandSheet demandSheet = demandSheetMapper.convertCreationDtoToEntity(demandSheetCreationDTO);
        BusinessUnit businessUnit = businessUnitService.findInternal(demandSheetCreationDTO.getBusinessUnitId());
        demandSheet.setBusinessUnit(businessUnit);
        BusinessRelationManager businessRelationManager = businessUnit.getBusinessRelationManager();
        if(businessRelationManager == null){
            throw new NoBRMAssignedToBusinessUnitException("No BRM assigned to this Business Unit");
        }
        demandSheet.setBusinessRelationManager(businessRelationManager);
        saveInternal(demandSheet);

        return demandSheetMapper.convertEntityToCreatedDto(demandSheet);
    }

    @Override
    public List<DemandSheetDTO> findAll() {
        return null;
    }

    @Override
    public DemandSheetDTO find(Long id) {
        return null;
    }

    @Override
    public List<DemandSheetDTO> findAllDemandSheetsOfBRMWithNoProject(Long brmId){
        List<DemandSheetDTO> result = new ArrayList<>();
        demandSheetDao.getAllDemandSheetsOfBRMWithNoProject(brmId).forEach(demandSheet -> result.add(demandSheetMapper.convertToDto(demandSheet)));
        return result;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public IDao getDao() {
        return demandSheetDao;
    }
}
