package com.misiek.dao;

import java.util.List;
import java.util.Optional;

public class BusinessUnitDao implements AbstractDao{

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
