package com.autoshow.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Producer DAO implementation.
 *
 * Created by maxim on 8.3.17.
 */
public class ProducerDaoImpl implements ProducerDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PRODUCER_ID = "producer_id";
    private static final String PRODUCER_NAME = "producer_name";
    private static final String COUNTRY = "country";
    private static final String AMOUNT_OF_CARS = "amount_of_cars";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${producer.select}")
    String getAllProducersSql;

    @Value("${producer.getAmountOfAllProducers}")
    String getAmountOfAllProducersSql;

    @Value("${producer.selectById}")
    String getProducerByIdSql;

    @Value("${producer.selectByName}")
    String getProducerByNameSql;

    @Value("${producer.selectProducerByCar}")
    String getProducerByCarSql;

    @Value("${producer.insert}")
    String insertProducerSql;

    @Value("${producer.update}")
    String updateProducerSql;

    @Value("${producer.delete}")
    String deleteProducerSql;


    public ProducerDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<ProducerWithAmount> getAllProducers() throws DataAccessException {
        LOGGER.debug("getAllProducers()");
        return jdbcTemplate.query(getAllProducersSql, new ProducerWithAmountRowMapper());
    }

    @Override
    public int getAmountOfAllProducers() throws DataAccessException {
        LOGGER.debug("getAmountOfAllProducers()");
        return jdbcTemplate.queryForObject(getAmountOfAllProducersSql, Integer.class);
    }

    @Override
    public ProducerWithAmount getProducerById(Integer producerId) throws DataAccessException {
        LOGGER.debug("getProducerById({})", producerId);
        SqlParameterSource namedParameters =
                new MapSqlParameterSource("p_producer_id", producerId);
        return namedParameterJdbcTemplate.queryForObject(
                getProducerByIdSql, namedParameters, new ProducerWithAmountRowMapper());
    }

    @Override
    public ProducerWithAmount getProducerByName(String name) throws DataAccessException {
        LOGGER.debug("getProducerByName({})", name);
        SqlParameterSource namedParameters =
                new MapSqlParameterSource("p_producer_name", name);
        return namedParameterJdbcTemplate.queryForObject(
                getProducerByNameSql, namedParameters, new ProducerWithAmountRowMapper());
    }

    @Override
    public ProducerWithAmount getProducerByCar(Integer carId) throws DataAccessException {
        LOGGER.debug("getProducerByCar(carId = {})", carId);
        SqlParameterSource namedParameters =
                new MapSqlParameterSource("p_car_id", carId);
        return namedParameterJdbcTemplate.queryForObject(
                getProducerByCarSql, namedParameters, new ProducerWithAmountRowMapper());
    }

    @Override
    public Integer addProducer(Producer producer) throws DataAccessException {
        LOGGER.debug("addProducer({})", producer.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("p_producer_id", producer.getProducerId());
        params.addValue("p_producer_name", producer.getName());
        params.addValue("p_country", producer.getCountry());
        namedParameterJdbcTemplate.update(insertProducerSql, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer updateProducer(Producer producer) throws DataAccessException {
        LOGGER.debug("updateProducer({})", producer.toString());
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("p_producer_id", producer.getProducerId());
        params.addValue("p_producer_name", producer.getName());
        params.addValue("p_country", producer.getCountry());
        return namedParameterJdbcTemplate.update(updateProducerSql, params);
    }

    @Override
    public Integer deleteProducer(Integer producerId) throws DataAccessException {
        LOGGER.debug("deleteProducer(id={})", producerId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("p_producer_id", producerId);
        return namedParameterJdbcTemplate.update(deleteProducerSql, params);
    }


    private class ProducerRowMapper implements RowMapper<Producer> {

        @Override
        public Producer mapRow(ResultSet resultSet, int i) throws SQLException {
            Producer producer = new Producer(
                    resultSet.getInt(PRODUCER_ID),
                    resultSet.getString(PRODUCER_NAME),
                    resultSet.getString(COUNTRY));
            return producer;
        }
    }


    private class ProducerWithAmountRowMapper implements RowMapper<ProducerWithAmount> {

        @Override
        public ProducerWithAmount mapRow(ResultSet resultSet, int i) throws SQLException {
            ProducerWithAmount producer = new ProducerWithAmount(
                    resultSet.getInt(PRODUCER_ID),
                    resultSet.getString(PRODUCER_NAME),
                    resultSet.getString(COUNTRY),
                    resultSet.getInt(AMOUNT_OF_CARS));
            return producer;
        }
    }
}
