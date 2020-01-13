package com.mbor.mapping;

import com.mbor.domain.Employee;
import com.mbor.model.EmployeeDTO;
import org.modelmapper.ModelMapper;

public abstract class EmployeeMapper<T extends EmployeeDTO, U extends Employee, S, V>  extends CreatMapper <T, U, S, V> {

    protected Class<T> dtoClazz;
    protected Class<U> entityClazz;
    protected Class<S> creationDtoClazz;
    protected Class<V> createdDtoClazz;

    public EmployeeMapper(ModelMapper modelMapper, Class<T> dtoClazz, Class<U> entityClazz, Class<S> creationDtoClazz, Class<V> createdDtoClazz) {
        super(modelMapper);
        this.dtoClazz = dtoClazz;
        this.entityClazz = entityClazz;
        this.creationDtoClazz = creationDtoClazz;
        this.createdDtoClazz = createdDtoClazz;
    }

    @Override
    public T convertToDto(U u) {
        return modelMapper.map(u, dtoClazz);
    }

    @Override
    public U convertToEntity(T t) {
        return modelMapper.map(t, entityClazz);
    }

    @Override
    public U convertCreationDtoToEntity(S s) {
        return modelMapper.map(s, entityClazz);
    }

    @Override
    public V convertEntityToCreatedDto(U u) {
        return modelMapper.map(u, createdDtoClazz);
    }
}
