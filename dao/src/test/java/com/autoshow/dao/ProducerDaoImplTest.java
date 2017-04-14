package com.autoshow.dao;

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
 * Created by maxim on 8.3.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class ProducerDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Producer testProducer =
            new Producer(10, "test_name", "country1");

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
        List<ProducerWithAmount> producers = producerDao.getAllProducers();
        Assert.assertTrue(producers.size() >= 2);
        Assert.assertTrue(producers.get(0).getAmountOfCars() == 2);
        Assert.assertTrue(producers.get(1).getAmountOfCars() == 1);
        Assert.assertTrue(producers.get(2).getAmountOfCars() == 2);
        Assert.assertTrue(producers.get(3).getAmountOfCars() == 2);
        Assert.assertTrue(producers.get(4).getAmountOfCars() == 2);
    }

    @Test
    public void getAmountOfAllProducersTest() throws Exception {
        LOGGER.debug("test: getAmountOfAllProducers()");
        Assert.assertEquals(5, producerDao.getAmountOfAllProducers());
    }

    @Test
    public void getProducerByIdTest() throws Exception {
        LOGGER.debug("test: getProducerById()");
        ProducerWithAmount producer = producerDao.getProducerById(1);
        Assert.assertNotNull(producer);
        Assert.assertEquals("Mercedes", producer.getName());
        Assert.assertEquals((Integer) 2, producer.getAmountOfCars());
    }

    @Test
    public void getProducerByNameTest() throws Exception {
        LOGGER.debug("test: getProducerByName()");
        ProducerWithAmount producer = producerDao.getProducerByName("BMW");
        Assert.assertNotNull(producer);
        Assert.assertEquals((Integer) 2, producer.getProducerId());
        Assert.assertEquals((Integer) 1, producer.getAmountOfCars());
    }

    @Test
    public void getProducerByCarTest() throws Exception {
        LOGGER.debug("test: getProducerByCar()");
        Integer carId = 3;
        ProducerWithAmount producer = producerDao.getProducerByCar(carId);
        Assert.assertEquals("BMW", producer.getName());
        Assert.assertEquals((Integer) 1, producer.getAmountOfCars());
    }

    @Test
    public void addProducerTest() throws Exception {
        LOGGER.debug("test: addProducer()");
        List<ProducerWithAmount> producers = producerDao.getAllProducers();
        Integer quantityBefore = producers.size();

        Integer producerId = producerDao.addProducer(testProducer);
        Assert.assertNotNull(producerId);

        ProducerWithAmount newProducer = producerDao.getProducerById(producerId);
        Assert.assertNotNull(newProducer);
        Assert.assertTrue(testProducer.getName().equals(newProducer.getName()));
        Assert.assertTrue(testProducer.getCountry().equals(newProducer.getCountry()));

        producers = producerDao.getAllProducers();
        Assert.assertEquals(quantityBefore + 1, producers.size());
    }

    @Test
    public void updateProducerTest() throws Exception {
        LOGGER.debug("test: updateProducer()");
        ProducerWithAmount producer = producerDao.getProducerById(1);
        Producer testProducer = new Producer();
        testProducer.setProducerId(producer.getProducerId());
        testProducer.setName("Updated name");
        testProducer.setCountry("Updated country");

        Integer amountOfUpdatedRecords = producerDao.updateProducer(testProducer);
        Assert.assertEquals(1, amountOfUpdatedRecords.intValue());

        ProducerWithAmount updatedProducer = producerDao.getProducerById(producer.getProducerId());
        Assert.assertEquals(testProducer.getName(), updatedProducer.getName());
        Assert.assertEquals(testProducer.getCountry(), updatedProducer.getCountry());
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