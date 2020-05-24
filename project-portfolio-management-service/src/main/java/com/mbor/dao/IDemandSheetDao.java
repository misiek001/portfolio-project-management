package com.mbor.dao;

import com.mbor.domain.DemandSheet;

import java.util.List;

public interface IDemandSheetDao extends IDao<DemandSheet> {

    List<DemandSheet> getAllDemandSheetsOfBRMWithNoProject(Long brmId);
}
