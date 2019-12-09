package com.misiek.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BusinessUnitDaoImpl implements AbstractDao{

    @Override
    public Optional save(Object o) {
        return Optional.empty();
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public Optional find(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
