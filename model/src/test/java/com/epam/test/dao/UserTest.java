package com.epam.test.dao;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by maxim on 13.2.17.
 */
public class UserTest {

    public static final int USER_ID = 20;

    @Test
    public void getUserId() throws Exception
    {
        User user = new User();
        user.setUserId(20);
        Assert.assertEquals("User id:", (Integer) USER_ID, user.getUserId());
    }

    @Test
    public void getLogin() throws Exception
    {
        User user = new User();
        user.setLogin("login");
        Assert.assertEquals("User login:", "login", user.getLogin());
    }

    @Test
    public void getPassword() throws Exception
    {
        User user = new User();
        user.setPassword("1234");
        Assert.assertEquals("User password:", "1234", user.getPassword());
    }

    @Test
    public void getDescription() throws Exception
    {
        User user = new User();
        user.setDescription("description");
        Assert.assertEquals("User description:", "description", user.getDescription());
    }
}
