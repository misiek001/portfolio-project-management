package com.mbor.service;

import com.mbor.dao.IDao;
import org.hibernate.HibernateException;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public abstract class RawService<T> implements IService<T> {

    @Override
    public T saveInternal(T t) {
        try {
            Optional<T> result = getDao().save(t);
            return result.orElseThrow(RuntimeException::new);
        } catch (HibernateException e){
            throw new RuntimeException("Unexpected error during saving:  " + e.getMessage());
        }
    }

    @Override
    public List<T> findAll() {
        return getDao().findAll();
    }

    @Override
    public T find(Long id) {
          Optional<T> result =  getDao().find(id);
          if(result.isPresent()){
              return result.get();
          } else {
              throw new NoResultException("No resource with id: " + id);
          }
    }

    @Override
    public void delete(Long id) {
        getDao().delete(id);
    }

    public abstract IDao getDao();

}


