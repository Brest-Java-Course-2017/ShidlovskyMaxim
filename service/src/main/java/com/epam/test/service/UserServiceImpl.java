package com.epam.test.service;

import com.epam.test.dao.User;
import com.epam.test.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.util.List;

/**
 * Created by maxim on 21.2.17.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() throws DataAccessException {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(Integer userId) throws DataAccessException {
        return null;
    }

    @Override
    public User getUserByLogin(String login) throws DataAccessException {
        LOGGER.debug("getUserByLogin(): user login = {} ", login);
        Assert.hasText(login, "User login should not be null.");
        return userDao.getUserByLogin(login);
    }

    @Override
    public Integer addUser(User user) throws DataAccessException {
        Assert.notNull(user, "User should not be null.");
        LOGGER.debug("addUser(): user login = {} ", user.getLogin());
        Assert.isNull(user.getUserId(), "User's ID should be empty.");
        Assert.hasText(user.getLogin(), "User's login should not be empty.");
        Assert.hasText(user.getPassword(), "User's password should not be empty.");
        //TODO: check login
        return userDao.addUser(user);
    }

    @Override
    public int updateUser(User user) throws DataAccessException {
        Assert.notNull(user, "User should not be null.");
        LOGGER.debug("updateUser(): user id = {} ", user.getUserId());
        Assert.hasText(user.getLogin(), "User's login should not be empty.");
        Assert.hasText(user.getPassword(), "User's password should not be empty.");

        return userDao.addUser(user);
    }

    @Override
    public int deleteUser(Integer userId) throws DataAccessException {
        Assert.notNull(userId, "User's ID should not be null.");
        Assert.notNull(userId, "The user should exist in the database.");
        LOGGER.debug("deleteUser(): user id = {} ", userId);

        return userDao.deleteUser(userId);
    }
}