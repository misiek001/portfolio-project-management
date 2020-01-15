package com.mbor.mapper;

public interface PojoMapper<T, U> {

    T convertToDto(U u);

    U convertToEntity(T t);

}
