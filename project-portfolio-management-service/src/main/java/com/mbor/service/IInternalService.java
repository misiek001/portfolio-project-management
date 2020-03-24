package com.mbor.service;

import java.util.List;

public interface IInternalService<T> {

    T saveInternal(T t);

    List<T> findAllInternal();

    T findInternal(Long id);

    void deleteInternal(Long id);

    T updateInternal(T t);

}
