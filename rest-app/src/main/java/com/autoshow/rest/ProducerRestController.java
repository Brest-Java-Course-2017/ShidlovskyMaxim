package com.autoshow.rest;

import com.autoshow.dao.Car;
import com.autoshow.dao.Producer;
import com.autoshow.service.ProducerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by maxim on 12.3.17.
 */
@CrossOrigin
@RestController
public class ProducerRestController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    ProducerService producerService;

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({IllegalArgumentException.class})
    public String incorrectDataError() {
        return "{  \"response\" : \"Incorrect Data Error\" }";
    }

    @RequestMapping(value = "/producers", method = RequestMethod.GET)
    public @ResponseBody List<Producer> getAllProducers() {
        LOGGER.debug("getAllProducers()");
        return producerService.getAllProducers();
    }


    @RequestMapping(value = "/producers/amount", method = RequestMethod.GET)
    public @ResponseBody int getAmountOfAllProducers() {
        LOGGER.debug("getAmountOfAllProducers()");
        return producerService.getAmountOfAllProducers();
    }

    @RequestMapping(value = "/producer/{id}", method = RequestMethod.GET)
    public @ResponseBody Producer getProducerById(@PathVariable(value = "id") Integer producerId) {
        LOGGER.debug("getProducerById({})", producerId);
        return producerService.getProducerById(producerId);
    }

    @RequestMapping(value = "/producer/name/{name}", method = RequestMethod.GET)
    public @ResponseBody Producer getProducerByName(@PathVariable(value = "name") String name) {
        LOGGER.debug("getProducerByName({})", name);
        return producerService.getProducerByName(name);
    }

    @RequestMapping(value = "/producer/car/{id}", method = RequestMethod.GET)
    public @ResponseBody Producer getProducerByCar(@PathVariable(value = "id") Integer carId) {
        LOGGER.debug("getProducerByCar({})", carId);
        return producerService.getProducerByCar(carId);
    }

    @RequestMapping(value = "/producer/{id}/cars/amount")
    public @ResponseBody int getAmountOfProducersCars(@PathVariable(value = "id") Integer producerId) {
        LOGGER.debug("getAmountOfProducerCars({})", producerId);
        return producerService.getAmountOfProducersCars(producerId);
    }

    @RequestMapping(value = "/producer", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody Integer addProducer(@RequestBody Producer producer) {
        LOGGER.debug("addProducer({})", producer);
        return producerService.addProducer(producer);
    }

    @RequestMapping(value = "/producer", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void updateProducer(@RequestBody Producer producer) {
        LOGGER.debug("updateProducer({})", producer);
        producerService.updateProducer(producer);
    }

    @RequestMapping(value = "/producer/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteProducer(@PathVariable(value = "id") Integer producerId) {
        LOGGER.debug("deleteProducer(id={})", producerId);
        producerService.deleteProducer(producerId);
    }
}
