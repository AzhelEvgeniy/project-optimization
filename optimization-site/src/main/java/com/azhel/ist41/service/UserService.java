package com.azhel.ist41.service;

import com.azhel.ist41.dao.exception.DuplicateUserNameException;
import com.azhel.ist41.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void addUser(User user) throws DuplicateUserNameException;
}
