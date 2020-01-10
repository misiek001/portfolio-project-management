package com.mbor.service;

import java.util.List;

public interface IService<T> {

    T saveInternal(T t);

    List<T> findAll();

    T find(Long id);

    void delete(Long id);

}
