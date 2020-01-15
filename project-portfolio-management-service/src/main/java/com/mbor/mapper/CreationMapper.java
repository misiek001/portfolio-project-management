package com.mbor.mapper;

public interface CreationMapper<T, U, S, V> extends PojoMapper<T, U> {

    U convertCreationDtoToEntity(S s);

    V convertEntityToCreatedDto(U u);

}
