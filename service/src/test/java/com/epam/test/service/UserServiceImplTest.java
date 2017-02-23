package com.epam.test.service;

import com.epam.test.dao.User;
import org.junit.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Created by maxim on 22.2.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:service-test.xml"})
@Transactional
public class UserServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final User user = new User("userLogin3", "userPassword3");

    static final String USER_LOGIN_1 = "userLogin1";

    @Autowired
    UserService userService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        LOGGER.error("execute: setUpBeforeClass()");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        LOGGER.error("execute: tearDownAfterClass()");
    }

    @Before
    public void beforeTest() throws Exception {
        LOGGER.error("execute: beforeTest()");
    }

    @After
    public void afterTest() throws Exception {
        LOGGER.error("execute: afterTest()");
    }


    @Test
    public void getAllUsers() throws Exception {
        LOGGER.debug("test: getAllUsers()");
        List<User> users = userService.getAllUsers();
        Assert.assertEquals("", 2, users.size());
    }

    @Test
    public void getUserById() throws Exception {
        LOGGER.debug("test: getUserById()");
        int testId = 1;
        User user = userService.getUserById(testId);
        Assert.assertNotNull(user);
        Assert.assertTrue(user.getUserId() == testId);
    }

    @Test
    public void getUserByLogin() throws Exception {
        LOGGER.debug("test: getUserByLogin()");
        User user = userService.getUserByLogin(USER_LOGIN_1);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getLogin());
        Assert.assertEquals(USER_LOGIN_1, user.getLogin());
    }

    @Test
    public void addUser() throws Exception {
        LOGGER.debug("test: addUser()");
        Assert.assertTrue(userService.addUser(user) == 1);
    }

    @Test
    public void updateUser() throws Exception {
        LOGGER.debug("test: updateUser()");
        user.setDescription("Updated description.");
        Assert.assertTrue(userService.updateUser(user) == 1);
    }

    @Test
    public void deleteUser() throws Exception {
        LOGGER.debug("test: deleteUser()");
        Assert.assertTrue(userService.deleteUser(user.getUserId()) == 1);
    }

}