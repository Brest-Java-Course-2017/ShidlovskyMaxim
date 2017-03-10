package com.autoshow.service;

import com.autoshow.dao.Car;
import com.autoshow.dao.CarDao;
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

import java.sql.Date;
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

    private static final Car car = new Car(6, "testModel", new Date(100, 1, 1), 20);

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
    public void getAllCars() throws Exception {
        LOGGER.debug("mockTest: getAllCars()");
        List<Car> cars = new ArrayList<Car>();
        EasyMock.expect(mockCarDao.getAllCars()).andReturn(cars);
        EasyMock.replay(mockCarDao);
        Assert.assertEquals(cars, carService.getAllCars());
    }

    @Test
    public void getCarsForReleaseTimePeriod() throws Exception {
        LOGGER.debug("mockTest: getCarsForReleaseTimePeriod()");
        List<Car> cars = new ArrayList<Car>();
        cars.add(new Car(1, "testModel1", new Date(50, 1,1), 5));
        cars.add(new Car(2, "testModel2", new Date(60, 1, 1), 5));
        EasyMock.expect(mockCarDao.getCarsForReleaseTimePeriod(
                new Date(40, 1, 1), new Date(70, 1, 1))).andReturn(cars);
        EasyMock.replay(mockCarDao);
        List<Car> testCars = carService.getCarsForReleaseTimePeriod(
                new Date(40, 1, 1), new Date(70, 1, 1));
        Assert.assertEquals(cars, testCars);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getCarById() throws Exception {
        LOGGER.debug("mockTest: getCarById()");
        EasyMock.expect(mockCarDao.getCarById(car.getCarId()))
                .andThrow(new UnsupportedOperationException());
        EasyMock.replay(mockCarDao);
        carService.getCarById(car.getCarId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getCarByModel() throws Exception {
        LOGGER.debug("mockTest: getCarByModel()");
        EasyMock.expect(mockCarDao.getCarByModel(car.getModel()))
                .andThrow(new UnsupportedOperationException());
        EasyMock.replay(mockCarDao);
        carService.getCarByModel(car.getModel());
    }

    @Test
    public void addCar() throws Exception {
        LOGGER.debug("mockTest: addCar()");
        EasyMock.expect(mockCarDao.addCar(car)).andReturn(6);
        EasyMock.replay(mockCarDao);
        Integer newId = carService.addCar(car);
        Assert.assertEquals(car.getCarId(), newId);
    }

    @Test
    public void updateCar() throws Exception {
        LOGGER.debug("mockTest: updateCar()");
        car.setModel("Updated model");
        car.setReleaseDate(new Date(80, 1, 1));
        car.setAmount(25);
        EasyMock.expect(mockCarDao.updateCar(car)).andReturn(1);
        EasyMock.replay(mockCarDao);
        int amountOfUpdatedRecords = carService.updateCar(car);
        Assert.assertEquals(1, amountOfUpdatedRecords);
    }

    @Test
    public void deleteCar() throws Exception {
        LOGGER.debug("mockTest: deleteCar()");
        EasyMock.expect(mockCarDao.deleteCar(1)).andReturn(1);
        EasyMock.replay(mockCarDao);
        int amountOfUpdatedRecords = carService.deleteCar(1);
        Assert.assertEquals(1, amountOfUpdatedRecords);
    }
}
