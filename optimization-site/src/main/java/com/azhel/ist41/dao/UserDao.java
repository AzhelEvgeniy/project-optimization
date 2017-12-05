package com.azhel.ist41.dao;

import com.azhel.ist41.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    void addUser(User user);
}
