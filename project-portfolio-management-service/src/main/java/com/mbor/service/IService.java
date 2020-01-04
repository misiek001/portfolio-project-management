package com.mbor.service;

import java.util.List;
import java.util.Optional;

public interface IService<T> {

    Optional<T> saveInternal(T t);

    List<T> findAll();

    Optional<T> find(Long id);

    void delete(Long id);

}
