package com.autoshow.service;

import com.autoshow.dao.Car;
import com.autoshow.dao.CarDao;
import com.autoshow.dao.CarWithProducerName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;

/**
 * Created by maxim on 10.3.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:car-service-mock-test.xml"})
public class CarServiceImplMockTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Car car =
            new Car(6, "testModel", new Date(100, 1, 1), 20, 1);

    @Autowired
    private CarService carService;

    @Autowired
    private CarDao mockCarDao;

    @After
    public void clean() {
        LOGGER.debug("mockTest: clean()");
        EasyMock.verify(mockCarDao);
        EasyMock.reset(mockCarDao);
    }


    @Test
    public void getAllCarsTest() throws Exception {
        LOGGER.debug("mockTest: getAllCars()");
        List<CarWithProducerName> cars = new ArrayList<CarWithProducerName>();
        EasyMock.expect(mockCarDao.getAllCars()).andReturn(cars);
        EasyMock.replay(mockCarDao);
        Assert.assertEquals(cars, carService.getAllCars());
    }

    @Test
    public void getCarsByProducerIdTest() throws Exception {
        LOGGER.debug("mockTest: getCarsByProducerId()");
        List<CarWithProducerName> cars = new ArrayList<CarWithProducerName>();
        CarWithProducerName car = new CarWithProducerName(6, "testModel",
                new Date(100, 1, 1),20, 1, "testProducerName");
        cars.add(car);
        EasyMock.expect(mockCarDao.getCarsByProducerId(1)).andReturn(cars);
        EasyMock.replay(mockCarDao);
        List<CarWithProducerName> receivedCars = carService.getCarsByProducerId(1);
        Assert.assertNotNull(receivedCars);
        Assert.assertTrue(receivedCars.size() == 1);
        CarWithProducerName testCar = receivedCars.get(0);
        Assert.assertNotNull(testCar);
        Assert.assertEquals(car, testCar);
        Assert.assertEquals(car.getCarId(), testCar.getCarId());
        Assert.assertEquals(car.getModel(), testCar.getModel());
        Assert.assertEquals(car.getReleaseDate(), testCar.getReleaseDate());
        Assert.assertEquals(car.getAmount(), testCar.getAmount());
    }

    @Test
    public void getAmountOfAllTypesOfModelsOfCarsTest() throws Exception {
        LOGGER.debug("mockTest: getAmountOfAllTypesOfModelsOfCars()");
        EasyMock.expect(mockCarDao.getAmountOfAllTypesOfModelsOfCars()).andReturn(100);
        EasyMock.replay(mockCarDao);
        Assert.assertTrue(carService.getAmountOfAllTypesOfModelsOfCars() == 100);
    }

    @Test
    public void getCarsForReleaseTimePeriodTest() throws Exception {
        LOGGER.debug("mockTest: getCarsForReleaseTimePeriod()");
        List<CarWithProducerName> cars = new ArrayList<CarWithProducerName>();
        cars.add(new CarWithProducerName(1, "testModel1", new Date(50, 1,1),
                5, 1, "testProducerName1"));
        cars.add(new CarWithProducerName(2, "testModel2", new Date(60, 1, 1),
                5, 1, "testProducerName2"));
        EasyMock.expect(mockCarDao.getCarsForReleaseTimePeriod(
                new Date(40, 1, 1), new Date(70, 1, 1))).andReturn(cars);
        EasyMock.replay(mockCarDao);
        List<CarWithProducerName> testCars = carService.getCarsForReleaseTimePeriod(
                new Date(40, 1, 1), new Date(70, 1, 1));
        Assert.assertEquals(cars, testCars);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getCarByIdTest() throws Exception {
        LOGGER.debug("mockTest: getCarById()");
        EasyMock.expect(mockCarDao.getCarById(car.getCarId()))
                .andThrow(new UnsupportedOperationException());
        EasyMock.replay(mockCarDao);
        carService.getCarById(car.getCarId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getCarByModelTest() throws Exception {
        LOGGER.debug("mockTest: getCarByModel()");
        EasyMock.expect(mockCarDao.getCarByModel(car.getModel()))
                .andThrow(new UnsupportedOperationException());
        EasyMock.replay(mockCarDao);
        carService.getCarByModel(car.getModel());
    }

    @Test
    public void addCarTest() throws Exception {
        LOGGER.debug("mockTest: addCar()");
        Car testCar = new Car(null, "testModel",
                new Date(100, 1, 1), 12, 1);
        CarWithProducerName carWithName = new CarWithProducerName(null, "testModel",
                new Date(100, 1, 1), 12, 1, "testName");
        EasyMock.expect(mockCarDao.addCar(testCar)).andReturn(6);
        EasyMock.expect(mockCarDao.getCarById(6)).andReturn(carWithName);
        EasyMock.replay(mockCarDao);
        Integer newId = carService.addCar(testCar);
        CarWithProducerName adderCar = carService.getCarById(newId);
        Assert.assertEquals(testCar.getModel(), adderCar.getModel());
        Assert.assertEquals(testCar.getReleaseDate(), adderCar.getReleaseDate());
        Assert.assertEquals(testCar.getAmount(), adderCar.getAmount());
        Assert.assertEquals(testCar.getProducerId(), adderCar.getProducerId());
    }

    @Test
    public void updateCarTest() throws Exception {
        LOGGER.debug("mockTest: updateCar()");
        car.setModel("Updated model");
        car.setReleaseDate(new Date(80, 1, 1));
        car.setAmount(25);
        car.setProducerId(2);
        EasyMock.expect(mockCarDao.updateCar(car)).andReturn(1);
        EasyMock.replay(mockCarDao);
        int amountOfUpdatedRecords = carService.updateCar(car);
        Assert.assertEquals(1, amountOfUpdatedRecords);
    }

    @Test
    public void deleteCarTest() throws Exception {
        LOGGER.debug("mockTest: deleteCar()");
        EasyMock.expect(mockCarDao.deleteCar(1)).andReturn(1);
        EasyMock.replay(mockCarDao);
        int amountOfUpdatedRecords = carService.deleteCar(1);
        Assert.assertEquals(1, amountOfUpdatedRecords);
    }
}
