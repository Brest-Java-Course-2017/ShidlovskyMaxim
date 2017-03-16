package com.autoshow.dao;

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
 * Created by maxim on 8.3.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class ProducerDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Producer testProducer =
            new Producer(3, "test_name", "country1");

    @Autowired
    ProducerDao producerDao;

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
        List<Producer> producers = producerDao.getAllProducers();
        Assert.assertTrue(producers.size() >= 2);
    }

    @Test
    public void getAmountOfAllProducersTest() throws Exception {
        LOGGER.debug("test: getAmountOfAllProducers()");
        Assert.assertEquals(2, producerDao.getAmountOfAllProducers());
    }

    @Test
    public void getProducerByIdTest() throws Exception {
        LOGGER.debug("test: getProducerById()");
        Producer producer = producerDao.getProducerById(1);
        Assert.assertNotNull(producer);
        Assert.assertEquals("Mercedes", producer.getName());
    }

    @Test
    public void getProducerByNameTest() throws Exception {
        LOGGER.debug("test: getProducerByName()");
        Producer producer = producerDao.getProducerByName("BMW");
        Assert.assertNotNull(producer);
        Assert.assertEquals((Integer) 2, producer.getProducerId());
    }

    @Test
    public void getProducerByCarTest() throws Exception {
        LOGGER.debug("test: getProducerByCar()");
        Car car = new Car(3, "X5", new Date(103,1,1), 30);
        Producer producer = producerDao.getProducerByCar(car);
        Assert.assertEquals("BMW", producer.getName());
    }

    @Test
    public void getAmountOfProducersCarsTest() throws Exception {
        LOGGER.debug("test: getAmountOfProducersCars()");
        Producer producer = new Producer();
        producer.setProducerId(1);
        int amountOfProducersCars = producerDao.getAmountOfProducersCars(producer);
        Assert.assertEquals(2, amountOfProducersCars);
    }

    @Test
    public void addProducerTest() throws Exception {
        LOGGER.debug("test: addProducer()");
        List<Producer> producers = producerDao.getAllProducers();
        Integer quantityBefore = producers.size();

        Integer producerId = producerDao.addProducer(testProducer);
        Assert.assertNotNull(producerId);

        Producer newProducer = producerDao.getProducerById(producerId);
        Assert.assertNotNull(newProducer);
        Assert.assertTrue(testProducer.getName().equals(newProducer.getName()));
        Assert.assertTrue(testProducer.getCountry().equals(newProducer.getCountry()));

        producers = producerDao.getAllProducers();
        Assert.assertEquals(quantityBefore + 1, producers.size());
    }

    @Test
    public void updateProducerTest() throws Exception {
        LOGGER.debug("test: updateProducer()");
        Producer producer = producerDao.getProducerById(1);
        producer.setName("Updated name");
        producer.setCountry("Updated country");

        Integer amountOfAddedRecords = producerDao.updateProducer(producer);
        Assert.assertEquals(1, amountOfAddedRecords.intValue());

        Producer updatedProducer = producerDao.getProducerById(producer.getProducerId());
        Assert.assertEquals(producer.getName(), updatedProducer.getName());
        Assert.assertEquals(producer.getCountry(), updatedProducer.getCountry());
    }

    @Test
    public void deleteProducerTest() throws Exception {
        LOGGER.debug("test: deleteProducer()");
        int quantityBeforeDeleting = producerDao.getAllProducers().size();
        producerDao.deleteProducer(1);
        int quantityAfterDeleting = producerDao.getAllProducers().size();
        Assert.assertEquals(quantityBeforeDeleting - 1, quantityAfterDeleting);
    }
}