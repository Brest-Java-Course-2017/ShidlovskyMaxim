package com.autoshow.dao;

import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.List;

/**
 * Car DAO interface.
 *
 * Created by maxim on 5.3.17.
 */
public interface CarDao {

    /**
     * Get all cars.
     *
     * @return A list of cars.
     * @throws DataAccessException
     */
    public List<CarWithProducerName> getAllCars() throws DataAccessException;

    /**
     * Get cars by producer's ID which produced the cars.
     *
     * @return A list of cars produced by particular producer.
     * @throws DataAccessException
     */
    public List<CarWithProducerName> getCarsByProducerId(Integer producerId) throws DataAccessException;

    /**
     * Get an amount of all types of models of the cars.
     *
     * @return Amn amount of types of models.
     * @throws DataAccessException
     */
    public int getAmountOfAllTypesOfModelsOfCars() throws DataAccessException;
    /**
     * Get a list of cars for release time period.
     *
     * @param from  Beginning date of releasing for filtering.
     * @param to Ending date of releasing for filtering.
     * @return A list of cars for release time period.
     */
    public List<CarWithProducerName> getCarsForReleaseTimePeriod(Date from, Date to)
            throws DataAccessException;

    /**
     * Get a car by ID.
     *
     * @param carId A car's ID.
     * @return A car.
     * @throws DataAccessException
     */
    public Car getCarById(Integer carId) throws DataAccessException;

    /**
     * Get a cat by model.
     *
     * @param model A car's model.
     * @return A car.
     * @throws DataAccessException
     */
    public Car getCarByModel(String model) throws DataAccessException;

    /**
     * Add a car.
     *
     * @param car A car.
     * @return New car ID.
     * @throws DataAccessException
     */
    public Integer addCar(Car car) throws DataAccessException;

    /**
     * Update a car.
     *
     * @param car A car.
     * @return An amount of updated records by Spring JDBC template.
     * @throws DataAccessException
     */
    public Integer updateCar(Car car) throws DataAccessException;

    /**
     * Delete a car by ID.
     *
     * @param carId A car's ID.
     * @return An amount of deleted records by Spring JDBC template.
     * @throws DataAccessException
     */
    public Integer deleteCar(Integer carId) throws DataAccessException;
}