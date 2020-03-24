package com.mbor.service;

import java.util.List;

public interface IAPIService<U, T, V> {

    List<V> findAll();

    V find(Long id);

    U save(T t);

    void delete(Long id);

}
