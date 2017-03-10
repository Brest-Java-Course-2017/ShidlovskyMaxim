package com.autoshow.service;

import com.autoshow.dao.Producer;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by maxim on 10.3.17.
 */
public interface ProducerService {

    /**
     * Get all producers.
     *
     * @return A list of producers.
     * @throws DataAccessException
     */
    public List<Producer> getAllProducers() throws DataAccessException;

    /**
     * Get a producer by ID.
     *
     * @param producerId A producer's ID.
     * @return A producer.
     * @throws DataAccessException
     */
    public Producer getProducerById(Integer producerId) throws DataAccessException;

    /**
     * Get a producer by name.
     *
     * @param name A name of producer company.
     * @return A producer.
     * @throws DataAccessException
     */
    public Producer getProducerByName(String name) throws DataAccessException;

    /**
     * Add a producer.
     *
     * @param producer A producer.
     * @return New car ID.
     * @throws DataAccessException
     */
    public Integer addProducer(Producer producer) throws DataAccessException;

    /**
     * Update a producer.
     *
     * @param producer A producer
     * @return An amount of updated records by Spring JDBC template.
     * @throws DataAccessException
     */
    public Integer updateProducer(Producer producer) throws DataAccessException;

    /**
     * Delete a producer by ID.
     *
     * @param producerId A producer's ID.
     * @return An amount of deleted records by Spring JDBC template.
     * @throws DataAccessException
     */
    public Integer deleteProducer(Integer producerId) throws DataAccessException;
}
