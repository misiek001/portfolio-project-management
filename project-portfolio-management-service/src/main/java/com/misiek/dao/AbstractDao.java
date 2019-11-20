package com.misiek.dao;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<T> {

    Optional<T> save(T t);

    List<T> findAll();

    Optional<T> find(Long id);

    void delete(Long id);

}
