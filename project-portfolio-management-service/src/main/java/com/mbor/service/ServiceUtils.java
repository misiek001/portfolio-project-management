package com.mbor.service;

import com.mbor.exception.WrongEmployeeTypeException;

public class ServiceUtils {

    public static  <T, R> R tryCast(Class<T> t, Object r) {
        if (t.isInstance(r)) {
            return (R) r;
        } else {
            throw new WrongEmployeeTypeException(r + " is not employee type:" + t);
        }
    }

}
