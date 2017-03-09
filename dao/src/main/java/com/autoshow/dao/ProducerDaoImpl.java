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

    static final String PRODUCER_ID = "producer_id";
    static final String PRODUCER_NAME = "producer_name";
    static final String COUNTRY = "country";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${producer.select}")
    String getAllProducersSql;

    @Value("${producer.selectById}")
    String getProducerByIdSql;

    @Value("${producer.selectByName}")
    String getProducerByNameSql;

    @Value("${producer.insert}")
    String insertProducerSql;

    @Value("${producer.update}")
    String updateProducerSql;

    @Value("${producer.delete}")
    String deleteProducerSql;

    @Value("${producer.deleteProducerFromBindingTable}")
    String deleteProducerFromBindingTableSql;

    public ProducerDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Producer> getAllProducers() throws DataAccessException {
        LOGGER.debug("getAllProducers()");
        return jdbcTemplate.query(getAllProducersSql, new ProducerRowMapper());
    }

    @Override
    public Producer getProducerById(Integer producerId) throws DataAccessException {
        LOGGER.debug("getProducerById({})", producerId);
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_producer_id", producerId);
        Producer producer = namedParameterJdbcTemplate.queryForObject(
                getProducerByIdSql, namedParameters, new ProducerRowMapper());
        return producer;
    }

    @Override
    public Producer getProducerByName(String name) throws DataAccessException {
        LOGGER.debug("getProducerByName({})", name);
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_producer_name", name);
        Producer producer = namedParameterJdbcTemplate.queryForObject(
                getProducerByNameSql, namedParameters, new ProducerRowMapper());
        return producer;
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
        namedParameterJdbcTemplate.update(deleteProducerFromBindingTableSql, params);
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
}
