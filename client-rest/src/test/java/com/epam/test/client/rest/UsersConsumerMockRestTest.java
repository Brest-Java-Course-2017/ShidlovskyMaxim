package com.epam.test.client.rest;

import com.epam.test.client.rest.api.UsersConsumer;
import com.epam.test.dao.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by maxim on 1.3.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:client-test-mock.xml"})
public class UsersConsumerMockRestTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final User user = new User(1, "userLogin1", "userPassword1", "");
    private static final User user3 = new User(3, "userLogin3", "userPassword3", "");
    private static final String USER_LOGIN_3 = "userLogin3";
    @Autowired
    UsersConsumer usersConsumer;
    @Value("${user.protocol}://${user.host}:${user.port}")
    private String hostUrl;
    @Value("${point.users}")
    private String urlUsers;
    @Value("${point.user}")
    private String urlUser;
    @Autowired
    private RestTemplate mockRestTemplate;

    @After
    public void tearDown() throws Exception {
        LOGGER.debug("test: tearDown()");
        reset(mockRestTemplate);
    }

    @Test
    public void getAllUsers() throws Exception {

        LOGGER.debug("test: getAllUsers()");
        List expectedResult = new ArrayList(2);
        expectedResult.add(user);
        expectedResult.add(user3);
        expect(mockRestTemplate.getForEntity(hostUrl + "/" + urlUsers, List.class))
                .andReturn(new ResponseEntity<List>(expectedResult, HttpStatus.OK));
        replay(mockRestTemplate);

        List<User> users = usersConsumer.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    public void getUserByLogin() throws Exception {

        LOGGER.debug("test: getUserByLogin()");
        expect(mockRestTemplate.getForEntity(hostUrl + "/" + urlUser + "/login/" + "userLogin3", User.class))
                .andReturn(new ResponseEntity<User>(user3, HttpStatus.FOUND));
        replay(mockRestTemplate);

        usersConsumer.getUserByLogin("userLogin3");
        assertEquals(USER_LOGIN_3, user3.getLogin());
    }

    @Test
    public void getUserById() throws Exception {

        LOGGER.debug("test: getUserById()");
        expect(mockRestTemplate.getForEntity(hostUrl + "/" + urlUser + "/" + 1, User.class))
                .andReturn(new ResponseEntity<User>(user3, HttpStatus.FOUND));
        replay(mockRestTemplate);

        usersConsumer.getUserById(1);
        assertEquals(1, (int) user3.getUserId());
    }

    @Test
    public void addUser() throws Exception {

        LOGGER.debug("test: addUser()");
        User testUser = new User(10, "userLogin10", "userPassword10", "");
        expect(mockRestTemplate.postForEntity(hostUrl + "/" + urlUser, testUser, User.class))
                .andReturn(new ResponseEntity<User>(testUser, HttpStatus.FOUND));
        replay(mockRestTemplate);

        int expectedId = usersConsumer.addUser(testUser);
        assertEquals(10, expectedId );
    }

    @Test
    public void updateUser() throws Exception {

        LOGGER.debug("test: updateUser()");
        User testUser = new User(10,"userLogin10", "userPassword10","After update");
        expect(mockRestTemplate).times(1);
        replay(mockRestTemplate);

        usersConsumer.updateUser(testUser);
    }

    @Test
    public void deleteUser() throws Exception {

        LOGGER.debug("test: updateUser()");
        expect(mockRestTemplate).times(1);
        replay(mockRestTemplate);

        usersConsumer.deleteUser(10);
    }
}
