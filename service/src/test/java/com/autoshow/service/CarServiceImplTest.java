package com.autoshow.service;

import com.autoshow.dao.Car;
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
@ContextConfiguration(locations = {"classpath:car-service-test.xml"})
@Transactional
public class CarServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Car car = new Car(5, "testModel", new Date(100, 1, 1), 50);

    @Autowired
    CarService carService;

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
    public void getAllCars() throws Exception {
        LOGGER.debug("test: getAllCars()");
        List<Car> cars = carService.getAllCars();
        Assert.assertEquals(3, cars.size());
    }

    @Test
    public void getCarsForReleaseTimePeriod() throws Exception {
        LOGGER.debug("test: getCarsForReleaseTimePeriod()");
        // Time period from 2012-11-10 to 2015-11-10
        List<Car> cars = carService.getCarsForReleaseTimePeriod(new Date(112, 10, 10), new Date(115, 10, 10));
        Assert.assertEquals(2, cars.size());
    }

    @Test
    public void getCarById() throws Exception {
        LOGGER.debug("test: getCarById()");
        int testId = 1;
        Car testCar = carService.getCarById(testId);
        Assert.assertNotNull(testCar);
        Assert.assertTrue(testCar.getCarId() == testId);
    }

    @Test
    public void getCarByModel() throws Exception {
        LOGGER.debug("test: getCarByModel()");
        String testModel = "X5";
        Car testCar = carService.getCarByModel(testModel);
        Assert.assertNotNull(testCar);
        Assert.assertTrue(testCar.getModel().equals(testModel));
    }

    @Test
    public void addCar() throws Exception {
        LOGGER.debug("test: addCar()");
        carService.addCar(car);
        Assert.assertEquals(car, carService.getCarById(car.getCarId()));
    }

    @Test
    public void updateCar() throws Exception {
        LOGGER.debug("test: updateCar()");
        Car testCar = carService.getCarById(1);
        testCar.setModel("Updated model");
        testCar.setReleaseDate(new Date(108, 1, 1));
        testCar.setAmount(555);
        carService.updateCar(testCar);
        Car updatedCar = carService.getCarById(1);
        Assert.assertEquals(testCar, updatedCar);
    }

    @Test
    public void deleteCar() throws Exception {
        LOGGER.debug("test: deleteCar()");
        int quantityBeforeDeleting = carService.getAllCars().size();
        carService.deleteCar(1);
        int quantityAfterDeleting = carService.getAllCars().size();
        Assert.assertTrue(quantityBeforeDeleting == (quantityAfterDeleting + 1));
    }
}
