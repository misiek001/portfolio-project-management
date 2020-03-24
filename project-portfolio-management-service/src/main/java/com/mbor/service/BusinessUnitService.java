package com.mbor.service;

import com.mbor.dao.IBusinessUnitDao;
import com.mbor.dao.IDao;
import com.mbor.domain.BusinessUnit;
import com.mbor.mapper.BusinessUnitMapper;
import com.mbor.model.BusinessUnitDTO;
import com.mbor.model.creation.BusinessUnitCreatedDTO;
import com.mbor.model.creation.BusinessUnitCreationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BusinessUnitService extends RawService<BusinessUnit> implements IBusinessUnitService {

    private final IBusinessUnitDao businessUnitDao;

    private final BusinessUnitMapper businessUnitMapper;

    @Autowired
    public BusinessUnitService(IBusinessUnitDao businessUnitDao, BusinessUnitMapper businessUnitMapper) {
        this.businessUnitDao = businessUnitDao;
        this.businessUnitMapper = businessUnitMapper;
    }

    @Override
    public BusinessUnitCreatedDTO save(BusinessUnitCreationDTO businessUnitCreationDTO) {
        BusinessUnit businessUnit = businessUnitMapper.convertCreationDtoToEntity(businessUnitCreationDTO);
        businessUnit = super.saveInternal(businessUnit);
        return businessUnitMapper.convertEntityToCreatedDto(businessUnit);
    }

    @Override
    public List<BusinessUnitDTO> findAll() {
        return super.findAllInternal().stream()
                .map(businessUnitMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BusinessUnitDTO find(Long id) {
        return businessUnitMapper.convertToDto(findInternal(id));
    }

    @Override
    public void delete(Long id) {
        deleteInternal(id);
    }

    @Override
    public IDao getDao() {
        return businessUnitDao;
    }
}
