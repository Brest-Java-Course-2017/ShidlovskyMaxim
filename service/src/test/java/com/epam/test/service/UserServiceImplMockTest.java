package com.epam.test.service;

import com.epam.test.dao.UserDao;
import com.epam.test.dao.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * Created by maxim on 23.2.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:service-test-mock.xml"})
public class UserServiceImplMockTest {

    @Autowired
    UserDao mockUserDao;

    @Autowired
    UserService userService;

    private static final Logger LOGGER = LogManager.getLogger();

    //Variable for tests.
    private static final User user = new User("userLogin3", "userPassword3");

    @After
    public void clean() {
        LOGGER.debug("mockTest: clean()");
        EasyMock.verify(mockUserDao);
        EasyMock.reset(mockUserDao);
    }

    @Test
    public void addUser() throws Exception {
        LOGGER.debug("mockTest: addUser()");
        EasyMock.expect(mockUserDao.addUser(new User("userLogin3", "userPassword3"))).andReturn(5);
        EasyMock.replay(mockUserDao);
        Integer id = userService.addUser(user);
        Assert.isTrue(id == 5);
    }

    @Test
    public void getAllUsers() throws Exception {
        LOGGER.debug("mockTest: getAllUsers()");
        EasyMock.expect(userService.getAllUsers()).andReturn(null);
        EasyMock.replay(mockUserDao);
        userService.getAllUsers();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getUserById() {
        LOGGER.debug("mockTest: getUserById()");
        EasyMock.expect(userService.getUserById(user.getUserId())).andThrow(new UnsupportedOperationException());
        EasyMock.replay(mockUserDao);
        userService.getUserById(user.getUserId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getUserByLogin() {
        LOGGER.debug("mockTest: getUserByLogin()");
        EasyMock.expect(userService.getUserByLogin(user.getLogin())).andThrow(new UnsupportedOperationException());
        EasyMock.replay(mockUserDao);
        userService.getUserByLogin(user.getLogin());
    }

    @Test
    public void updateUser() throws Exception {
        LOGGER.debug("mockTest: updateUser()");
        user.setDescription("Updated description.");
        EasyMock.expect(userService.updateUser(user)).andReturn(1);
        EasyMock.replay(mockUserDao);
        userService.updateUser(user);
    }

    @Test
    public void deleteUser() throws Exception {
        LOGGER.debug("mockTest: deleteUser()");
        EasyMock.expect(userService.deleteUser(user.getUserId())).andReturn(1);
        EasyMock.replay(mockUserDao);
        userService.deleteUser(user.getUserId());
    }
}
