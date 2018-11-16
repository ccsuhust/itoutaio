package com.iexample.itoutaio.service;

import com.iexample.itoutaio.dao.UserDao;
import com.iexample.itoutaio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    public User getUser(int id)
    {
        return userDao.selectUser(id);
    }
}
