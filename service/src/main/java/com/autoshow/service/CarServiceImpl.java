package com.autoshow.service;

import com.autoshow.dao.Car;
import com.autoshow.dao.CarDao;
import com.autoshow.dao.CarWithProducerName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * Created by maxim on 10.3.17.
 */
@Service
@Transactional
public class CarServiceImpl implements CarService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    CarDao carDao;

    public void setCarDao(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public List<CarWithProducerName> getAllCars() throws DataAccessException {
        LOGGER.debug("getAllCars()");
        return carDao.getAllCars();
    }

    @Override
    public List<CarWithProducerName> getCarsByProducerId(Integer producerId) throws DataAccessException {
        LOGGER.debug("getCarsByProducerId({})", producerId);
        Assert.notNull(producerId, "Producer's ID mustn't be null.");
        return carDao.getCarsByProducerId(producerId);
    }

    @Override
    public int getAmountOfAllTypesOfModelsOfCars() throws DataAccessException {
        LOGGER.debug("getAmountOfAllTypesOfModelsOfCars()");
        return carDao.getAmountOfAllTypesOfModelsOfCars();
    }

    @Override
    public List<CarWithProducerName> getCarsForReleaseTimePeriod(Date from, Date to) throws DataAccessException {
        LOGGER.debug("getCarsForReleaseTimePeriod(from {} to {})", from.toString(), to.toString());
        Assert.notNull(from, "The beginning of the period mustn't be null");
        Assert.notNull(to, "The end of the period mustn't be null");
        Assert.isTrue(from.before(to), "The beginning of the period mustn't be later then the end.");
        return carDao.getCarsForReleaseTimePeriod(from, to);
    }

    @Override
    public Car getCarById(Integer carId) throws DataAccessException {
        LOGGER.debug("getCarById({})", carId);
        Assert.notNull(carId, "Car's ID mustn't be null.");
        return carDao.getCarById(carId);
    }

    @Override
    public Car getCarByModel(String model) throws DataAccessException {
        LOGGER.debug("getCarByModel({})", model);
        Assert.notNull(model, "Car's model mustn't be null.");
        Assert.hasText(model, "Car's model must have text.");
        return carDao.getCarByModel(model);
    }

    @Override
    public Integer addCar(Car car) throws DataAccessException {
        LOGGER.debug("addCar({})", car);
        Assert.notNull(car, "Car mustn't be null.");
        Assert.isNull(car.getCarId(), "Car's ID must be null.");
        Assert.notNull(car.getModel(), "Car's model mustn't be null.");
        Assert.hasText(car.getModel(), "Car's model must have text.");
        Assert.notNull(car.getReleaseDate(), "Car's release date mustn't be null.");
        Assert.notNull(car.getAmount(), "An amount of cars mustn't be null.");
        Assert.notNull(car.getProducerId(), "Producer's ID mustn't be null.");
        return carDao.addCar(car);
    }

    @Override
    public Integer updateCar(Car car) throws DataAccessException {
        LOGGER.debug("updateCar({})", car);
        Assert.notNull(car, "Car mustn't be null.");
        Assert.notNull(car.getModel(), "Car's model mustn't be null.");
        Assert.hasText(car.getModel(), "Car's model must have text.");
        Assert.notNull(car.getReleaseDate(), "Car's release date mustn't be null.");
        Assert.notNull(car.getAmount(), "An amount of cars mustn't be null.");
        Assert.notNull(car.getProducerId(), "Producer's ID mustn't be null.");
        return carDao.updateCar(car);
    }

    @Override
    public Integer deleteCar(Integer carId) throws DataAccessException {
        LOGGER.debug("deleteCar(id={})", carId);
        Assert.notNull(carId, "Car's ID mustn't be null.");
        return carDao.deleteCar(carId);
    }
}
