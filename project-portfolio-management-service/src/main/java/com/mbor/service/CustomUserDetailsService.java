package com.mbor.service;

import com.mbor.domain.security.User;

public interface CustomUserDetailsService {
    User loadUserByUserName(String s);
}
