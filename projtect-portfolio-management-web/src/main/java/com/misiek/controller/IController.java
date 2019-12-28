package com.misiek.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IController<T> {

    T save(T t);

    ResponseEntity<List<T>> findAll();

    ResponseEntity<T> find(Long id);

    ResponseEntity<Void> delete(Long id);
}
