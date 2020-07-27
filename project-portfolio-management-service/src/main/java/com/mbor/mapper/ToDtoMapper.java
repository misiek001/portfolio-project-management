package com.mbor.mapper;

import org.modelmapper.ModelMapper;

public abstract class ToDtoMapper<T, U> implements IToDtoMapper<T, U> {

    protected final ModelMapper modelMapper;

    public ToDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

}
