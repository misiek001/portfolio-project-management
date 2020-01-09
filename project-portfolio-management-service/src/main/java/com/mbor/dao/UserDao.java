package com.mbor.dao;

import com.mbor.domain.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends RawDao<User> {

    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
