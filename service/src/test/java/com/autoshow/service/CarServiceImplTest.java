package com.autoshow.service;

import com.autoshow.dao.Car;
import com.autoshow.dao.CarWithProducerName;
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
 * Created by maxim on 10.3.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:car-service-test.xml"})
@Transactional
public class CarServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Car car =
            new Car(22, "testModel", new Date(100, 1, 1), 50, 1);

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
    public void getAllCarsTest() throws Exception {
        LOGGER.debug("test: getAllCars()");
        List<CarWithProducerName> cars = carService.getAllCars();
        Assert.assertEquals(9, cars.size());
    }

    @Test
    public void getCarsByProducerIdTest() throws Exception {
        LOGGER.debug("test: getCarsByProducerId()");
        List<CarWithProducerName> cars = carService.getCarsByProducerId(2);
        Assert.assertNotNull(cars);
        Assert.assertTrue(cars.size() == 1);
        CarWithProducerName car = cars.get(0);
        Assert.assertNotNull(car);
        Assert.assertEquals((Integer) 3, car.getCarId());
        Assert.assertEquals("X5", car.getModel());
        Assert.assertEquals(new Date(106, 0, 1), car.getReleaseDate());
        Assert.assertEquals((Integer) 30, car.getAmount());
        Assert.assertEquals("BMW", car.getProducerName());
    }

    @Test
    public void getAmountOfAllTypesOfModelsOfCarsTest() throws Exception {
        LOGGER.debug("test: getAmountOfAllTypesOfModelsOfCars()");
        Assert.assertTrue(carService.getAmountOfAllTypesOfModelsOfCars() == 9);
    }

    @Test
    public void getCarsForReleaseTimePeriodTest() throws Exception {
        LOGGER.debug("test: getCarsForReleaseTimePeriod()");
        // Time period from 2012-11-10 to 2015-11-10
        List<CarWithProducerName> cars = carService.getCarsForReleaseTimePeriod(
                new Date(112, 10, 10), new Date(115, 10, 10));
        Assert.assertEquals(1, cars.size( ));
    }

    @Test
    public void getCarByIdTest() throws Exception {
        LOGGER.debug("test: getCarById()");
        int testId = 1;
        CarWithProducerName testCar = carService.getCarById(testId);
        Assert.assertNotNull(testCar);
        Assert.assertTrue(testCar.getCarId() == testId);
    }

    @Test
    public void getCarByModelTest() throws Exception {
        LOGGER.debug("test: getCarByModel()");
        String testModel = "X5";
        CarWithProducerName testCar = carService.getCarByModel(testModel);
        Assert.assertNotNull(testCar);
        Assert.assertTrue(testCar.getModel().equals(testModel));
    }

    @Test
    public void addCarTest() throws Exception {
        LOGGER.debug("test: addCar()");
        Car testCar = new Car(null, "testModel",
                new Date(100, 1, 1), 10, 1);
        Integer newId = carService.addCar(testCar);
        CarWithProducerName addedCar = carService.getCarById(newId);
        Assert.assertEquals(testCar.getModel(), addedCar.getModel());
        Assert.assertEquals(testCar.getReleaseDate(), addedCar.getReleaseDate());
        Assert.assertEquals(testCar.getAmount(), addedCar.getAmount());
        Assert.assertEquals(testCar.getProducerId(), addedCar.getProducerId());
    }

    @Test
    public void updateCarTest() throws Exception {
        LOGGER.debug("test: updateCar()");
        CarWithProducerName testCar = carService.getCarById(1);
        testCar.setModel("Updated model");
        testCar.setReleaseDate(new Date(108, 1, 1));
        testCar.setAmount(555);
        testCar.setProducerId(2);
        Car testForUpdateCar = new Car(testCar.getCarId(), testCar.getModel(),
                testCar.getReleaseDate(), testCar.getAmount(), testCar.getProducerId());
        carService.updateCar(testForUpdateCar);
        CarWithProducerName updatedCar = carService.getCarById(1);
        Assert.assertEquals(testCar.getModel(), updatedCar.getModel());
        Assert.assertEquals(testCar.getReleaseDate(), updatedCar.getReleaseDate());
        Assert.assertEquals(testCar.getAmount(), updatedCar.getAmount());
        Assert.assertEquals(testCar.getProducerId(), updatedCar.getProducerId());
    }

    @Test
    public void deleteCarTest() throws Exception {
        LOGGER.debug("test: deleteCar()");
        int quantityBeforeDeleting = carService.getAllCars().size();
        carService.deleteCar(1);
        int quantityAfterDeleting = carService.getAllCars().size();
        Assert.assertTrue(quantityBeforeDeleting == (quantityAfterDeleting + 1));
    }
}
