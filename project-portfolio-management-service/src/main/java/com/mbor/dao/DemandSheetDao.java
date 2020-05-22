package com.mbor.dao;

import com.mbor.domain.DemandSheet;

import org.springframework.stereotype.Repository;

@Repository
public class DemandSheetDao extends RawDao<DemandSheet> implements IDemandSheetDao {

    public DemandSheetDao() {
        this.clazz = DemandSheet.class;
    }
}
