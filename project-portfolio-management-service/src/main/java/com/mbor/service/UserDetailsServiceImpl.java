package com.mbor.service;

import com.mbor.dao.IUserDao;
import com.mbor.domain.security.Role;
import com.mbor.domain.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {

    private final IUserDao userDao;

    @Autowired
    public UserDetailsServiceImpl(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User>  result = userDao.findByUsername(s);
        if(result.isPresent()){
           User user = result.get();
           return new org.springframework.security.core.userdetails.User(user.getEmployee().getUserName(), user.getPassword(), getAuthorities(user.getRoles()));
        } else {
            throw new UsernameNotFoundException("User Not Found");
        }
    }

    @Override
    public User loadUserByUserName(String name) {
        Optional<User> result =  userDao.findByUsername(name);
        if(result.isPresent()){
            return result.get();
        } else {
            throw new NoResultException("No user with name: " + name);
        }
    }



    public final Set<? extends GrantedAuthority> getAuthorities(final Set<Role> roles) {
        return roles.stream()
                .flatMap(role -> role.getPrivileges()
                        .stream())
                .map(p -> new SimpleGrantedAuthority(p.getName()))
                .collect(Collectors.toSet());
    }
}
