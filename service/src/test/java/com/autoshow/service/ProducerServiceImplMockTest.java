package com.autoshow.service;

import com.autoshow.dao.Car;
import com.autoshow.dao.Producer;
import com.autoshow.dao.ProducerDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.easymock.EasyMock;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxim on 10.3.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:producer-service-mock-test.xml"})
public class ProducerServiceImplMockTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Producer producer = new Producer(6, "testName2", "testCountry");

    @Autowired
    private ProducerService producerService;

    @Autowired
    private ProducerDao mockProducerDao;

    @After
    public void clean() {
        LOGGER.debug("mockTest: clean()");
        EasyMock.verify(mockProducerDao);
        EasyMock.reset(mockProducerDao);
    }

    @Test
    public void getAllProducersTest() throws Exception {
        LOGGER.debug("mockTest: getAllProducers()");
        List<Producer> producers = new ArrayList<Producer>();
        EasyMock.expect(mockProducerDao.getAllProducers()).andReturn(producers);
        EasyMock.replay(mockProducerDao);
        Assert.assertEquals(producers, producerService.getAllProducers());
    }

    @Test
    public void getAmountOfAllProducersTest() throws Exception {
        LOGGER.debug("mockTest: getAmountOfAllProducers()");
        EasyMock.expect(mockProducerDao.getAmountOfAllProducers()).andReturn(10);
        EasyMock.replay(mockProducerDao);
        Assert.assertEquals(10, producerService.getAmountOfAllProducers());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getProducerByIdTest() throws Exception {
        LOGGER.debug("mockTest: getProducerById()");
        EasyMock.expect(mockProducerDao.getProducerById(producer.getProducerId()))
                .andThrow(new UnsupportedOperationException());
        EasyMock.replay(mockProducerDao);
        producerService.getProducerById(producer.getProducerId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getProducerByNameTest() throws Exception {
        LOGGER.debug("mockTest: getProducerByName()");
        EasyMock.expect(mockProducerDao.getProducerByName(producer.getName()))
                .andThrow(new UnsupportedOperationException());
        EasyMock.replay(mockProducerDao);
        producerService.getProducerByName(producer.getName());
    }

    @Test
    public void getProducerByCarTest() throws Exception {
        LOGGER.debug("mockTest: getProducerByCar()");
        Car car = new Car(1, "testModel", new Date(100, 0, 1), 30);
        EasyMock.expect(mockProducerDao.getProducerByCar(car)).andReturn(producer);
        EasyMock.replay(mockProducerDao);
        Producer receivedProducer = producerService.getProducerByCar(car);
        Assert.assertNotNull(receivedProducer);
        Assert.assertEquals(producer.getProducerId(), receivedProducer.getProducerId());
        Assert.assertEquals(producer.getName(), receivedProducer.getName());
        Assert.assertEquals(producer.getCountry(), receivedProducer.getCountry());
    }

    @Test
    public void getAmountOfProducersCarsTest() throws Exception {
        LOGGER.debug("mockTest: getAmountOfProducersCars()");
        EasyMock.expect(mockProducerDao.getAmountOfProducersCars(producer)).andReturn(100);
        EasyMock.replay(mockProducerDao);
        Assert.assertEquals(100, producerService.getAmountOfProducersCars(producer));
    }

    @Test
    public void addProducerTest() throws Exception {
        LOGGER.debug("mockTest: addProducer()");
        EasyMock.expect(mockProducerDao.addProducer(producer)).andReturn(6);
        EasyMock.replay(mockProducerDao);
        Integer newId = producerService.addProducer(producer);
        Assert.assertEquals(producer.getProducerId(), newId);
    }

    @Test
    public void updateProducerTest() throws Exception {
        LOGGER.debug("mockTest: updateProducer()");
        producer.setName("Updated name");
        producer.setCountry("Updated country");
        EasyMock.expect(mockProducerDao.updateProducer(producer)).andReturn(1);
        EasyMock.replay(mockProducerDao);
        int amountOfUpdatedRecords = producerService.updateProducer(producer);
        Assert.assertEquals(1, amountOfUpdatedRecords);
    }

    @Test
    public void deleteProducerTest() throws Exception {
        LOGGER.debug("mockTest: deleteProducer()");
        EasyMock.expect(mockProducerDao.deleteProducer(1)).andReturn(1);
        EasyMock.replay(mockProducerDao);
        int amountOfUpdatedRecords = producerService.deleteProducer(1);
        Assert.assertEquals(1, amountOfUpdatedRecords);
    }
}
