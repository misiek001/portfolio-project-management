package com.mbor.service;

import com.mbor.dao.UserDao;
import com.mbor.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User>  result = userDao.findByUsername(s);
        if(result.isPresent()){
           User user = result.get();
           return new org.springframework.security.core.userdetails.User(user.getEmployee().getUserName(), user.getPassword(), Collections.emptyList());
        } else {
            throw new UsernameNotFoundException("User Not Found");
        }

    }
}
