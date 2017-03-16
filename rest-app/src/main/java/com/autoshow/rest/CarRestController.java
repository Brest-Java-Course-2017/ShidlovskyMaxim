package com.autoshow.rest;

import com.autoshow.dao.Car;
import com.autoshow.dao.Producer;
import com.autoshow.service.CarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * Created by maxim on 12.3.17.
 */
@CrossOrigin
@RestController
public class CarRestController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    CarService carService;

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({IllegalArgumentException.class})
    public String incorrectDataError() {
        return "{  \"response\" : \"Incorrect Data Error\" }";
    }

    //curl -v localhost:8088/cars
    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    public @ResponseBody List<Car> getAllCars() {
        LOGGER.debug("getAllCars()");
        return carService.getAllCars();
    }

    @RequestMapping(value = "/producer/{producerId}/cars", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public @ResponseBody List<Car> getCarsByProducerId(
            @PathVariable(value = "producerId") Integer producerId) {
        LOGGER.debug("getCarsByProducerId({})", producerId);
        return carService.getCarsByProducerId(producerId);
    }

    /*
    public void getAmountOfAllTypesOfModelsOfCarsTest() {
        LOGGER.debug("getAmountOfAllTypesOfModelsOfCars()");

    }
    */

    @RequestMapping(value = "/car/period", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public @ResponseBody List<Car> getCarsForReleaseTimePeriod(
            @RequestParam(name = "from") Date from,
            @RequestParam(name = "to") Date to) {
        LOGGER.debug("getCarsForReleaseTimePeriod(from {} to {})", from.toString(), to.toString());
        return carService.getCarsForReleaseTimePeriod(from, to);
    }

    //curl -v localhost:8088/car/1
    @RequestMapping(value = "/car/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public @ResponseBody Car getCarById(@PathVariable(value = "id") Integer carId) {
        LOGGER.debug("getCarById({})", carId);
        return carService.getCarById(carId);
    }

    //curl -v localhost:8088/car/model/X5
    @RequestMapping(value = "/car/model/{model}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public @ResponseBody Car getCarByModel(@PathVariable(value = "model") String model) {
        LOGGER.debug("getCarByModel({})", model);
        return carService.getCarByModel(model);
    }

    //curl -H "Content-Type: application/json" -X POST -d '{"login":"xyz","password":"xyz"}' -v localhost:8088/car
    @RequestMapping(value = "/car", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody Integer addCar(@RequestBody Car car) {
        LOGGER.debug("addCar({})", car);
        return carService.addCar(car);
    }

    //curl -X PUT -v localhost:8088/car/2/m1/rd1/a1
    @RequestMapping(value = "/car", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void updateCar(@RequestBody Car car) {
        LOGGER.debug("updateCar({})", car);
        carService.updateCar(car);
    }

    @RequestMapping(value = "/car/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteCar(@PathVariable(value = "id") Integer carId) {
        LOGGER.debug("deleteCar(id={})", carId);
        carService.deleteCar(carId);
    }
}
