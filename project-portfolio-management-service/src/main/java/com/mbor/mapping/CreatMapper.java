package com.mbor.mapping;

import org.modelmapper.ModelMapper;

public abstract class CreatMapper<T, U, S, V> extends Mapper<T, U> implements CreationMapper<T, U, S, V> {

    public CreatMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

}
