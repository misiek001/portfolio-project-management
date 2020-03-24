package com.mbor.service;

import com.mbor.dao.IDao;
import org.hibernate.HibernateException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Transactional
public abstract class RawService<T> {

    public T saveInternal(T t) {
        try {
            Optional<T> result = getDao().save(t);
            return result.orElseThrow(RuntimeException::new);
        } catch (HibernateException e){
            throw new RuntimeException("Unexpected error during saving:  " + e.getMessage());
        }
    }

    public List<T> findAllInternal() {
        return getDao().findAll();
    }

    public T findInternal(Long id) {
          Optional<T> result =  getDao().find(id);
          if(result.isPresent()){
              return result.get();
          } else {
              throw new NoResultException("No resource with id: " + id);
          }
    }

    public T updateInternal(T t) {
        try {
            Optional<T> result = getDao().update(t);
            return result.orElseThrow(RuntimeException::new);
        } catch (HibernateException e){
            throw new RuntimeException("Unexpected error during merging:  " + e.getMessage());
        }

    }


    public void deleteInternal(Long id) {
        getDao().delete(id);
    }

    public abstract IDao getDao();

}


