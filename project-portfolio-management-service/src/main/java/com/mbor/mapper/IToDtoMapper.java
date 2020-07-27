package com.mbor.mapper;

public interface IToDtoMapper<T, U> {

    T convertEntityToDto(U u);

}
