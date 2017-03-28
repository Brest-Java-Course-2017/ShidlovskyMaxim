package com.autoshow.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Created by maxim on 5.3.17.
 */
public class CarTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Car car = new Car();

    @Test
    public void getCarIdTest() throws Exception {
        LOGGER.debug("test: getCarId()");
        car.setCarId(10);
        Assert.assertEquals((Integer) 10, car.getCarId());
    }

    @Test
    public void getModelTest() throws Exception {
        LOGGER.debug("test: getModel()");
        car.setModel("testModel");
        Assert.assertEquals("testModel", car.getModel());
    }

    @Test
    public void getReleaseDateTest() throws Exception {
        LOGGER.debug("test: getReleaseDate()");
        Date testDate = new Date(2017, 2, 1);
        car.setReleaseDate(testDate);
        Assert.assertEquals(testDate, car.getReleaseDate());
    }

    @Test
    public void getAmountTest() throws Exception {
        LOGGER.debug("test: getAmount()");
        car.setAmount(100);
        Assert.assertEquals((Integer) 100, car.getAmount());
    }
}