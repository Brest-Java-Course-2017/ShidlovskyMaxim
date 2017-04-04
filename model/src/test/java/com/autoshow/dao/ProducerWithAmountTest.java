package com.autoshow.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by maxim on 4.4.17.
 */
public class ProducerWithAmountTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final ProducerWithAmount producer = new ProducerWithAmount();

    @Test
    public void getProducerIdTest() throws Exception {
        LOGGER.debug("test: getProducerId()");
        producer.setProducerId(10);
        Assert.assertEquals((Integer) 10, producer.getProducerId());
    }

    @Test
    public void getNameTest() throws Exception {
        LOGGER.debug("test: getName()");
        producer.setName("testName");
        Assert.assertEquals("testName", producer.getName());
    }

    @Test
    public void getCountryTest() throws Exception {
        LOGGER.debug("test: getCountry()");
        producer.setCountry("testCountry");
        Assert.assertEquals("testCountry", producer.getCountry());
    }

    @Test
    public void getAmountOfCarsTest() throws Exception {
        LOGGER.debug("test: getAmountOfCars()");
        producer.setAmountOfCars(10);
        Assert.assertEquals((Integer) 10, producer.getAmountOfCars());
    }
}
