package com.misiek.controller;

import com.misiek.mapping.Mapper;
import com.misiek.service.IService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class RawController<T> implements IController<T> {


    @Override
    public T save(T t) {
        return (T) getService().save(t).get();
    }

    @GetMapping
    @Override
    public ResponseEntity<List<T>> findAll() {
        List<T> result = getService().findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<T> find(Long id) {
        T result = (T) getService().find(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        getService().delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public abstract IService getService();

    public abstract Mapper getMapper();
}
