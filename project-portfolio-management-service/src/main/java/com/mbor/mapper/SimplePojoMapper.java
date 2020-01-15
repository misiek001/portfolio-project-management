package com.mbor.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SimplePojoMapper<T, U> implements PojoMapper<T, U>  {

    protected final ModelMapper modelMapper;

    @Autowired
    public SimplePojoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

}
