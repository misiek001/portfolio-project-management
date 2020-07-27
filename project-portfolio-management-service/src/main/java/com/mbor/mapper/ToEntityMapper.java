package com.mbor.mapper;

import org.modelmapper.ModelMapper;

public abstract class ToEntityMapper<T, U> extends ToDtoMapper<T, U>  implements IToEntityMapper<T, U>  {

    public ToEntityMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
