package com.mbor.mapper;

public interface IToEntityMapper <T, U> {

    U convertDtoToEntity(T t);
}
