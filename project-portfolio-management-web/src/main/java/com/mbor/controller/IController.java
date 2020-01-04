package com.mbor.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IController{

    <U, T> U save(T t);

    <U> ResponseEntity<List<U>> findAll();

    <U> ResponseEntity<U> find(Long id);

    ResponseEntity<Void> delete(Long id);
}
