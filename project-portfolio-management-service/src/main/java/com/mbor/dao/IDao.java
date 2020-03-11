package com.mbor.dao;

import java.util.List;
import java.util.Optional;

public interface IDao<T> {

    Optional<T> save(T t);

    List<T> findAll();

    Optional<T> find(Long id);

    void delete(Long id);

    Optional<T> update(T t);

}
