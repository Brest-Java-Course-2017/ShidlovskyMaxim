package com.autoshow.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by maxim on 9.3.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class CarDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Car testCar =
            new Car(15, "test_model", new Date(1,1,2017), 100, 1);

    @Autowired
    CarDao carDao;

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
    public void getAllCarsTest() throws Exception {
        LOGGER.debug("test: getAllCars()");
        List<Car> cars = carDao.getAllCars();
        Assert.assertTrue(cars.size() >= 9);
    }

    @Test
    public void getCarsByProducerIdTest() throws Exception {
        LOGGER.debug("test: getCarsByProducerId()");
        List<Car> cars = carDao.getCarsByProducerId(2);
        Assert.assertNotNull(cars);
        Assert.assertTrue(cars.size() == 1);
        Car car = cars.get(0);
        Assert.assertNotNull(car);
        Assert.assertEquals((Integer) 3, car.getCarId());
        Assert.assertEquals("X5", car.getModel());
        Assert.assertEquals(new Date(106, 0, 1), car.getReleaseDate());
        Assert.assertEquals((Integer) 30, car.getAmount());
        Assert.assertEquals((Integer) 2, car.getProducerId());
    }

    @Test
    public void getAmountOfAllTypesOfModelsOfCarsTest() throws Exception {
        LOGGER.debug("test: getAmountOfAllTypesOfModelsOfCars()");
        Assert.assertTrue(carDao.getAmountOfAllTypesOfModelsOfCars() == 9);
    }

    @Test
    public void getCarsForReleaseTimePeriod() throws Exception {
        LOGGER.debug("test: getCarsForReleaseTimePeriod()");
        // Time period from 2012-11-10 to 2015-11-10
        List<Car> cars = carDao.getCarsForReleaseTimePeriod(
                new Date(112, 10, 10), new Date(115, 10, 10));
        Assert.assertEquals(1, cars.size());
    }

    @Test
    public void getCarByIdTest() throws Exception {
        LOGGER.debug("test: getCarById()");
        Car car = carDao.getCarById(1);
        Assert.assertNotNull(car);
        Assert.assertEquals("S500", car.getModel());
    }

    @Test
    public void getCarByModelTest() throws Exception {
        LOGGER.debug("test: getCarByModel()");
        Car car = carDao.getCarByModel("E220");
        Assert.assertNotNull(car);
        Assert.assertEquals((Integer) 2, car.getCarId());
    }

    @Test
    public void addCarTest() throws Exception {
        LOGGER.debug("test: addCar()");
        List<Car> cars = carDao.getAllCars();
        Integer quantityBefore = cars.size();

        Integer carId = carDao.addCar(testCar);
        Assert.assertNotNull(carId);

        Car newCar = carDao.getCarById(carId);
        Assert.assertNotNull(newCar);
        Assert.assertTrue(testCar.getModel().equals(newCar.getModel()));
        Assert.assertTrue(testCar.getReleaseDate().equals(newCar.getReleaseDate()));
        Assert.assertTrue(testCar.getAmount().equals(newCar.getAmount()));
        Assert.assertTrue(testCar.getProducerId().equals(newCar.getProducerId()));

        cars = carDao.getAllCars();
        Assert.assertEquals(quantityBefore + 1, cars.size());
    }

    @Test
    public void updateCarTest() throws Exception {
        LOGGER.debug("test: updateCar()");
        Car car = carDao.getCarById(1);
        car.setModel("Updated model");
        car.setReleaseDate(new Date(2, 2, 2017));
        car.setAmount(200);
        car.setProducerId(1);

        Integer amountOfUpdatedRecords = carDao.updateCar(car);
        Assert.assertEquals(1, amountOfUpdatedRecords.intValue());

        Car updatedCar = carDao.getCarById(car.getCarId());
        Assert.assertEquals(car.getModel(), updatedCar.getModel());
        Assert.assertEquals(car.getReleaseDate(), updatedCar.getReleaseDate());
        Assert.assertEquals(car.getAmount(), updatedCar.getAmount());
        Assert.assertEquals(car.getProducerId(), updatedCar.getProducerId());
    }

    @Test
    public void deleteCarTest() throws Exception {
        LOGGER.debug("test: deleteCar()");
        int quantityBeforeDeleting = carDao.getAllCars().size();
        carDao.deleteCar(1);
        int quantityAfterDeleting = carDao.getAllCars().size();
        Assert.assertEquals(quantityBeforeDeleting - 1, quantityAfterDeleting);
    }
}
