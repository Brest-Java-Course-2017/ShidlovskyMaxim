package com.autoshow.rest;

import com.autoshow.dao.Car;
import com.autoshow.dao.Producer;
import com.autoshow.dao.ProducerWithAmount;
import com.autoshow.service.CarService;
import com.autoshow.service.ProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
public class ProducerControllerMockTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Resource
    private ProducerRestController producerController;

    private MockMvc mockMvc;

    @Autowired
    private ProducerService mockProducerService;

    private Producer producer = new Producer(1, "testName", "testCountry");

    @Before
    public void setUp() {
        LOGGER.debug("test: setUp()");
        mockMvc = standaloneSetup(producerController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @After
    public void tearDown() {
        LOGGER.debug("test: tearDown()");
        verify(mockProducerService);
        reset(mockProducerService);
    }

    @Test
    public void getAllProducersTest() throws Exception {
        LOGGER.debug("test: getAllProducers()");
        expect(mockProducerService.getAllProducers()).andReturn(
                Arrays.<ProducerWithAmount>asList(
                        new ProducerWithAmount(8, "n", "c", 10)));
        replay(mockProducerService);

        mockMvc.perform(
                get("/producers")
                        .accept(MediaType.APPLICATION_JSON))
                            .andDo(print())
                            .andExpect(status().isOk());
    }

    @Test
    public void getAmountOfAllProducers() throws Exception {
        LOGGER.debug("test: tearDown()");
        expect(mockProducerService.getAmountOfAllProducers()).andReturn(100);
        replay(mockProducerService);

        mockMvc.perform(
                get("/producers/amount")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getProducerByIdTest() throws Exception {
        LOGGER.debug("test: getProducerById()");
        expect(mockProducerService.getProducerById(producer.getProducerId())).andReturn(producer);
        replay(mockProducerService);

        String producerString = new ObjectMapper().writeValueAsString(producer);

        mockMvc.perform(
                get("/producer/" + producer.getProducerId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(producerString));
    }

    @Test
    public void getProducerByNameTest() throws Exception {
        LOGGER.debug("test: getProducerByName()");
        expect(mockProducerService.getProducerByName(producer.getName())).andReturn(producer);
        replay(mockProducerService);

        String producerString = new ObjectMapper().writeValueAsString(producer);

        mockMvc.perform(
                get("/producer/name/" + producer.getName())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(producerString));
    }

    @Test
    public void getProducerByCarTest() throws Exception {
        LOGGER.debug("test: getProducerByCar()");
        expect(mockProducerService.getProducerByCar(anyObject(Integer.class))).andReturn(producer);
        replay(mockProducerService);

        mockMvc.perform(
                get("/producer/car/" + 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addProducerTest() throws Exception {
        LOGGER.debug("test: addProducer()");
        expect(mockProducerService.addProducer(anyObject(Producer.class))).andReturn(3);
        replay(mockProducerService);

        String producer = new ObjectMapper().writeValueAsString(
                new Producer(9, "name1", "country1"));

        mockMvc.perform(
                post("/producer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(producer))
                            .andDo(print())
                            .andExpect(status().isCreated())
                            .andExpect(content().string(    "3"));
    }


    @Test
    public void updateProducerTest() throws Exception {
        LOGGER.debug("test: updateProducer()");
        expect(mockProducerService.updateProducer(anyObject(Producer.class))).andReturn(0);
        replay(mockProducerService);

        String producer = new ObjectMapper().writeValueAsString(
                new Producer(9, "name1", "country1"));


        mockMvc.perform(
                put("/producer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(producer)
        ).andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().string(""));
    }

    @Test
    public void deleteProducerTest() throws Exception {
        LOGGER.debug("test: deleteProducer()");
        expect(mockProducerService.deleteProducer(anyObject(Integer.class))).andReturn(0);
        replay(mockProducerService);

        mockMvc.perform(
                delete("/producer/8")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }
}
