package com.autoshow.service;

import com.autoshow.dao.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by maxim on 10.3.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:producer-service-test.xml"})
@Transactional
public class ProducerServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Producer producer = new Producer(5, "testName", "testCountry");

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
    public void getAllProducers() throws Exception {
        LOGGER.debug("test: getAllProducers()");
        List<Producer> producers = producerService.getAllProducers();
        Assert.assertEquals(2, producers.size());
    }

    @Test
    public void getProducerById() throws Exception {
        LOGGER.debug("test: getProducerById()");
        int testId = 1;
        Producer testProducer = producerService.getProducerById(testId);
        Assert.assertNotNull(testProducer);
        Assert.assertTrue(testProducer.getProducerId() == testId);
    }

    @Test
    public void getProducerByName() throws Exception {
        LOGGER.debug("test: getProducerByName()");
        String testName = "BMW";
        Producer testProducer = producerService.getProducerByName(testName);
        Assert.assertNotNull(testProducer);
        Assert.assertTrue(testProducer.getName().equals(testName));
    }

    @Test
    public void addProducer() throws Exception {
        LOGGER.debug("test: addProducer()");
        producerService.addProducer(producer);
        Assert.assertEquals(producer, producerService.getProducerById(producer.getProducerId()));
    }

    @Test
    public void updateProducer() throws Exception {
        LOGGER.debug("test: updateProducer()");
        Producer testProducer = producerService.getProducerById(1);
        testProducer.setName("Updated name");
        testProducer.setCountry("Updated country");
        producerService.updateProducer(testProducer);
        Producer updatedProducer = producerService.getProducerById(1);
        Assert.assertEquals(testProducer, updatedProducer);
    }

    @Test
    public void deleteProducer() throws Exception {
        LOGGER.debug("test: deleteProducer()");
        int quantityBeforeDeleting = producerService.getAllProducers().size();
        producerService.deleteProducer(1);
        int quantityAfterDeleting = producerService.getAllProducers().size();
        Assert.assertTrue(quantityBeforeDeleting == (quantityAfterDeleting + 1));
    }
}
