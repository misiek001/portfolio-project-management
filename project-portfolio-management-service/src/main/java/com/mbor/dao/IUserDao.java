package com.mbor.dao;

import com.mbor.domain.security.User;

import java.util.Optional;

public interface IUserDao extends IDao<User> {

    Optional<User> findByUsername(String userName);

}
