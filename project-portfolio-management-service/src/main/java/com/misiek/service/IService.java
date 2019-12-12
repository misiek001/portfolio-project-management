package com.misiek.service;

import java.util.List;
import java.util.Optional;

public interface IService<T> {

    Optional<T> save(T t);

    List<T> findAll();

    Optional<T> find(Long id);

    void delete(Long id);

}
