package com.autoshow.service;

import com.autoshow.dao.Car;
import com.autoshow.dao.Producer;
import com.autoshow.dao.ProducerWithAmount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

/**
 * Created by maxim on 10.3.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:producer-service-test.xml"})
@Transactional
public class ProducerServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Producer producer =
            new Producer(null, "testName", "testCountry");

    @Autowired
    ProducerService producerService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        LOGGER.error("test: setUpBeforeClass()");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        LOGGER.error("test: tearDownAfterClass()");
    }

    @Before
    public void beforeTest() throws Exception {
        LOGGER.error("test: beforeTest()");
    }

    @After
    public void afterTest() throws Exception {
        LOGGER.error("test: afterTest()");
    }


    @Test
    public void getAllProducersTest() throws Exception {
        LOGGER.debug("test: getAllProducers()");
        List<ProducerWithAmount> producers = producerService.getAllProducers();
        Assert.assertEquals(5, producers.size());
    }

    @Test
    public void getAmountOfAllProducersTest() throws Exception {
        LOGGER.debug("test: getAmountOfAllProducers()");
        int amountOfAllProducers = producerService.getAmountOfAllProducers();
        Assert.assertEquals(5, amountOfAllProducers);
    }

    @Test
    public void getProducerByIdTest() throws Exception {
        LOGGER.debug("test: getProducerById()");
        int testId = 1;
        ProducerWithAmount testProducer = producerService.getProducerById(testId);
        Assert.assertNotNull(testProducer);
        Assert.assertTrue(testProducer.getProducerId() == testId);
    }

    @Test
    public void getProducerByNameTest() throws Exception {
        LOGGER.debug("test: getProducerByName()");
        String testName = "BMW";
        ProducerWithAmount testProducer = producerService.getProducerByName(testName);
        Assert.assertNotNull(testProducer);
        Assert.assertTrue(testProducer.getName().equals(testName));
    }

    @Test
    public void getProducerByCarTest() throws Exception {
        LOGGER.debug("test: getProducerByCar()");
        Integer carId = 3;
        ProducerWithAmount testProducer = producerService.getProducerByCar(carId);
        Assert.assertNotNull(testProducer);
        Assert.assertEquals((Integer) 2, testProducer.getProducerId());
        Assert.assertEquals("BMW", testProducer.getName());
        Assert.assertEquals("Germany", testProducer.getCountry());
    }

    @Test
    public void addProducerTest() throws Exception {
        LOGGER.debug("test: addProducer()");
        producerService.addProducer(producer);
        ProducerWithAmount addedProducer = producerService.getProducerByName(producer.getName());
        Assert.assertEquals(producer.getName(), addedProducer.getName());
        Assert.assertEquals(producer.getCountry(), addedProducer.getCountry());
    }

    @Test
    public void updateProducerTest() throws Exception {
        LOGGER.debug("test: updateProducer()");
        ProducerWithAmount testProducerWithAmount = producerService.getProducerById(1);
        Producer testProducer = new Producer();
        testProducer.setProducerId(testProducerWithAmount.getProducerId());
        testProducer.setName("Updated name");
        testProducer.setCountry("Updated country");
        producerService.updateProducer(testProducer);
        ProducerWithAmount updatedProducer = producerService.getProducerById(1);
        Assert.assertEquals(testProducer.getName(), updatedProducer.getName());
        Assert.assertEquals(testProducer.getCountry(), updatedProducer.getCountry());
    }

    @Test
    public void deleteProducerTest() throws Exception {
        LOGGER.debug("test: deleteProducer()");
        int quantityBeforeDeleting = producerService.getAllProducers().size();
        producerService.deleteProducer(1);
        int quantityAfterDeleting = producerService.getAllProducers().size();
        Assert.assertTrue(quantityBeforeDeleting == (quantityAfterDeleting + 1));
    }
}
