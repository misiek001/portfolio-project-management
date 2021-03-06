package com.mbor.mapper;

import org.modelmapper.ModelMapper;

public abstract class CreationPojoMapper<T, U, S, V> extends ToDtoMapper<T, U> implements CreationMapper<T, U, S, V> {

    public CreationPojoMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

}
