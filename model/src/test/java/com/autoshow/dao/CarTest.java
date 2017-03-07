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

    @Test
    public void getCarId() throws Exception {
        LOGGER.debug("test: getCarId()");
        Car car = new Car();
        car.setCarId(10);
        Assert.assertEquals((Integer) 10, car.getCarId());
    }

    @Test
    public void getModel() throws Exception {
        LOGGER.debug("test: getModel()");
        Car car = new Car();
        car.setModel("testModel");
        Assert.assertEquals("testModel", car.getModel());
    }

    @Test
    public void getReleaseDate() throws Exception {
        LOGGER.debug("test: getReleaseDate()");
        Car car = new Car();
        Date testDate = new Date(2017, 2, 1);
        car.setReleaseDate(testDate);
        Assert.assertEquals(testDate, car.getReleaseDate());
    }

    @Test
    public void getAmount() throws Exception {
        LOGGER.debug("test: getAmount()");
        Car car = new Car();
        car.setAmount(100);
        Assert.assertEquals((Integer) 100, car.getAmount());
    }
}