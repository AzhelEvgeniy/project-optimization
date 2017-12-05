package com.azhel.ist41.service;

import com.azhel.ist41.dao.UserDao;
import com.azhel.ist41.dao.exception.DuplicateUserNameException;
import com.azhel.ist41.model.User;
import com.azhel.ist41.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    BCryptPasswordEncoder bcrypt;

    @Override
    public void addUser(User user) throws DuplicateUserNameException {
        user.setPassword(bcrypt.encode(user.getPassword()));
        user.setEnabled(1);
        user.setRole(new HashSet<Role>(){{
            add(new Role(user.getUsername(), "ROLE_USER"));
        }});
        userDao.addUser(user);
    }
}
