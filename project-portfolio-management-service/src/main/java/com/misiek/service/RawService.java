package com.misiek.service;

import com.misiek.dao.IDao;

import java.util.List;
import java.util.Optional;

public abstract class RawService<T> implements IService<T> {

    @Override
    public Optional<T> save(T t) {
        return getDao().save(t);
    }

    @Override
    public List<T> findAll() {
        return getDao().findAll();
    }

    @Override
    public Optional<T> find(Long id) {
        return getDao().find(id);
    }

    @Override
    public void delete(Long id) {
        getDao().delete(id);
    }

    public abstract IDao getDao();

}


