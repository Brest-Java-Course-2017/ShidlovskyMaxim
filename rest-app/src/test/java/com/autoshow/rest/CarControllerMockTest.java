package com.autoshow.rest;

import com.autoshow.dao.Car;
import com.autoshow.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;

import java.sql.Date;
import java.util.Arrays;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by maxim on 12.3.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:rest-mock-test.xml"})
public class CarControllerMockTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Resource
    private CarRestController carController;

    private MockMvc mockMvc;

    @Autowired
    private CarService mockCarService;

    private Car car = new Car(1, "testModel", new Date(100, 1, 1), 100);

    @Before
    public void setUp() {
        LOGGER.debug("test: setUp()");
        mockMvc = standaloneSetup(carController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @After
    public void tearDown() {
        LOGGER.debug("test: tearDown()");
        verify(mockCarService);
        reset(mockCarService);
    }

    @Test
    public void getAllCarsTest() throws Exception {
        LOGGER.debug("test: getAllCars()");
        expect(mockCarService.getAllCars()).andReturn(
                Arrays.<Car>asList(new Car(8, "m", new Date(60, 1, 1), 44)));
        replay(mockCarService);

        mockMvc.perform(
                get("/cars")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getCarsByProducerIdTest() throws Exception {
        LOGGER.debug("test: getCarsByProducerId()");
        expect(mockCarService.getCarsByProducerId(anyObject(Integer.class)))
                .andReturn(Arrays.<Car>asList(car));
        replay(mockCarService);

        mockMvc.perform(
                get("/producer/1/cars")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    /*
    @Test
    public void getAmountOfAllTypesOfModelsOfCarsTest() throws Exception {
        LOGGER.debug("test: getAmountOfAllTypesOfModelsOfCars()");

    }
    */

    @Test
    public void getCarsForReleaseTimePeriodTest() throws Exception {
        LOGGER.debug("test: getCarsForReleaseTimePeriod()");
        expect(mockCarService.getCarsForReleaseTimePeriod(anyObject(Date.class), anyObject(Date.class)))
                .andReturn(Arrays.<Car>asList(car));
        replay(mockCarService);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("from", new Date(50, 1, 1).toString());
        params.add("to", new Date(60, 1, 1).toString());

        mockMvc.perform(
                get("/car/period").params(params)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    public void getCarByIdTest() throws Exception {
        LOGGER.debug("test: getCarById()");
        expect(mockCarService.getCarById(anyObject(Integer.class))).andReturn(car);
        replay(mockCarService);

        String carString = new ObjectMapper().writeValueAsString(car);

        mockMvc.perform(
                get("/car/" + car.getCarId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carString))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().string(carString));
    }

    @Test
    public void getCarByModelTest() throws Exception {
        LOGGER.debug("test: getCarByModel()");
        expect(mockCarService.getCarByModel(anyObject(String.class))).andReturn(car);
        replay(mockCarService);

        String carString = new ObjectMapper().writeValueAsString(car);

        mockMvc.perform(
                get("/car/model/" + car.getModel())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carString))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().string(carString));
    }

    @Test
    public void addCarTest() throws Exception {
        LOGGER.debug("test: addCar()");
        expect(mockCarService.addCar(anyObject(Car.class))).andReturn(3);
        replay(mockCarService);

        String carString = new ObjectMapper().writeValueAsString(car);

        mockMvc.perform(
                post("/car")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carString))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(    "3"));
    }

    @Test
    public void updateCarTest() throws Exception {
        LOGGER.debug("test: updateCar()");
        expect(mockCarService.updateCar(anyObject(Car.class))).andReturn(0);
        replay(mockCarService);

        String carString = new ObjectMapper().writeValueAsString(car);

        mockMvc.perform(
                put("/car")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carString)
        ).andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().string(""));
    }

    @Test
    public void deleteCarTest() throws Exception {
        LOGGER.debug("test: deleteCar()");
        expect(mockCarService.deleteCar(anyObject(Integer.class))).andReturn(0);
        replay(mockCarService);

        mockMvc.perform(
                delete("/car/8")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }
}
