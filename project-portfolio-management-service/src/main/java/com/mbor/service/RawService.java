package com.mbor.service;

import com.mbor.dao.IDao;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public abstract class RawService<T> implements IService<T> {

    @Override
    public Optional<T> saveInternal(T t) {
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


