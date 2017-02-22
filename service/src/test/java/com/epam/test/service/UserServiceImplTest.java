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
    public void beforeTest() {
        LOGGER.error("execute: beforeTest()");
    }

    @After
    public void afterTest() {
        LOGGER.error("execute: afterTest()");
    }


    @Test
    public void getAllUsers() throws Exception {
        LOGGER.debug("test: getAllUsers()");
        List<User> users =  userService.getAllUsers();
        assertTrue(users.size() > 0);
    }

    @Test
    public void getUserById() throws Exception {
        LOGGER.debug("test: getUserById()");
        User user = userService.getUserById(1);
        assertNotNull(user);
        assertEquals(USER_LOGIN_1, user.getLogin());
    }

    @Test
    public void getUserByLogin() throws Exception {
        LOGGER.debug("test: getUserByLogin()");
        User user = userService.getUserByLogin(USER_LOGIN_1);
        assertNotNull(user);
        assertEquals((Integer) 1, user.getUserId());
    }

    @Test
    public void addUser() throws Exception {
        LOGGER.debug("test: addUser()");

        List<User> users = userService.getAllUsers();
        Integer quantityBefore = users.size();

        Integer userId = userService.addUser(user);
        assertNotNull(userId);

        User newUser = userService.getUserById(userId);
        assertNotNull(newUser);
        assertTrue(user.getLogin().equals(newUser.getLogin()));
        assertTrue(user.getPassword().equals(newUser.getPassword()));
        assertNull(user.getDescription());

        users = userService.getAllUsers();
        assertEquals(quantityBefore + 1, users.size());
    }

    @Test
    public void updateUser() throws Exception {
        LOGGER.debug("test: updateUser()");
        User user = userService.getUserById(1);
        user.setPassword("updated password");
        user.setDescription("updated description");

        int count = userService.updateUser(user);
        assertEquals(1, count);

        User updatedUser = userService.getUserById(user.getUserId());
        assertTrue(user.getLogin().equals(updatedUser.getLogin()));
        assertTrue(user.getPassword().equals(updatedUser.getPassword()));
        assertTrue(user.getDescription().equals(updatedUser.getDescription()));
    }

    @Test
    public void deleteUser() throws Exception {
        LOGGER.debug("test: deleteUser()");

        Integer userId = userService.addUser(user);
        assertNotNull(userId);

        List<User> users = userService.getAllUsers();
        Integer quantityBefore = users.size();

        int count = userService.deleteUser(userId);
        assertEquals(1, count);


        users = userService.getAllUsers();
        assertEquals(quantityBefore - 1, users.size());
    }

}