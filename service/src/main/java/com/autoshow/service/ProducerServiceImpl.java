package com.autoshow.service;

import com.autoshow.dao.Producer;
import com.autoshow.dao.ProducerDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by maxim on 10.3.17.
 */
@Service
@Transactional
public class ProducerServiceImpl implements ProducerService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    ProducerDao producerDao;

    public void setProducerDao(ProducerDao producerDao) {
        this.producerDao = producerDao;
    }

    @Override
    public List<Producer> getAllProducers() throws DataAccessException {
        LOGGER.debug("getAllProducers()");
        return producerDao.getAllProducers();
    }

    @Override
    public Producer getProducerById(Integer producerId) throws DataAccessException {
        LOGGER.debug("getProducerById({})", producerId);
        Assert.notNull(producerId, "Producer's ID mustn't be null.");
        return producerDao.getProducerById(producerId);
    }

    @Override
    public Producer getProducerByName(String name) throws DataAccessException {
        LOGGER.debug("getProducerByName({})", name);
        Assert.notNull(name, "Producer's name mustn't be null.");
        Assert.hasText(name, "Producer's name must have text.");
        return producerDao.getProducerByName(name);
    }

    @Override
    public Integer addProducer(Producer producer) throws DataAccessException {
        LOGGER.debug("addProducer({})", producer);
        Assert.notNull(producer, "Producer mustn't be null.");
        Assert.notNull(producer.getProducerId(), "Producer's ID mustn't be null.");
        Assert.notNull(producer.getName(), "Producer's name mustn't be null.");
        Assert.hasText(producer.getName(), "Producer's name must have text.");
        return producerDao.addProducer(producer);
    }

    @Override
    public Integer updateProducer(Producer producer) throws DataAccessException {
        LOGGER.debug("updateProducer({})", producer);
        Assert.notNull(producer, "Producer mustn't be null.");
        Assert.notNull(producer.getName(), "Producer's name mustn't be null.");
        Assert.hasText(producer.getName(), "Producer's name must have text.");
        return producerDao.updateProducer(producer);
    }

    @Override
    public Integer deleteProducer(Integer producerId) throws DataAccessException {
        LOGGER.debug("deleteProducer(id={})", producerId);
        Assert.notNull(producerId, "Producer's ID mustn't be null.");
        return producerDao.deleteProducer(producerId);
    }
}
