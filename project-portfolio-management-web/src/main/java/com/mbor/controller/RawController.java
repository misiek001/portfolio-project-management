package com.mbor.controller;

import com.mbor.service.IAPIService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public abstract class RawController implements IController {

    @Override
    public <U, T> U save(T t) {
        return (U) getService().save(t);
    }

    @GetMapping
    @Override
    public <U>  ResponseEntity<List<U>> findAll() {
        List<U> result = getService().findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    public <U> ResponseEntity<U> find(Long id) {
        U result = (U) getService().find(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        getService().delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public abstract IAPIService getService();

}
