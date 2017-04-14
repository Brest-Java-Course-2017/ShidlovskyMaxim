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
import java.util.Date;
import java.util.List;

/**
 * Car DAO implementation.
 *
 * Created by maxim on 9.3.17.
 */
public class CarDaoImpl implements CarDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String CAR_ID = "car_id";
    private static final String MODEL = "model";
    private static final String RELEASE_DATE = "release_date";
    private static final String AMOUNT = "amount";
    private static final String PRODUCER_ID = "producer_id";
    private static final String PRODUCER_NAME = "producer_name";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${car.select}")
    String getAllCarsSql;

    @Value("${car.selectCarsByProducerId}")
    String getCarsByProducerIdSql;

    @Value("${car.getAmountOfAllTypesOfModelsOfCars}")
    String getAmountOfAllTypesOfModelsOfCarsSql;

    @Value("${car.selectForReleaseTimePeriod}")
    String getCarsForReleaseTimePeriodSql;

    @Value("${car.selectById}")
    String getCarByIdSql;

    @Value("${car.selectByModel}")
    String getCarByModelSql;

    @Value("${car.insert}")
    String insertCarSql;

    @Value("${car.update}")
    String updateCarSql;

    @Value("${car.delete}")
    String deleteCarSql;


    public CarDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<CarWithProducerName> getAllCars() throws DataAccessException {
        LOGGER.debug("getAllCars()");
        return jdbcTemplate.query(getAllCarsSql, new CarWithProducerNameRowMapper());
    }

    @Override
    public List<CarWithProducerName> getCarsByProducerId(Integer producerId) throws DataAccessException {
        LOGGER.debug("getCarsByProducerId({})", producerId);
        SqlParameterSource namedParameters =
                new MapSqlParameterSource("p_producer_id", producerId);
        return namedParameterJdbcTemplate.query(
                getCarsByProducerIdSql, namedParameters, new CarWithProducerNameRowMapper());
    }

    @Override
    public int getAmountOfAllTypesOfModelsOfCars() throws DataAccessException {
        LOGGER.debug("getAmountOfAllTypesOfModelsOfCars()");
        return jdbcTemplate.queryForObject(getAmountOfAllTypesOfModelsOfCarsSql, Integer.class);
    }

    @Override
    public List<CarWithProducerName> getCarsForReleaseTimePeriod(Date from, Date to) throws DataAccessException {
        LOGGER.debug("getCarsForReleaseTimePeriod(from {} to {})", from.toString(), to.toString());
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("p_from", from);
        params.addValue("p_to", to);
        return namedParameterJdbcTemplate.query(getCarsForReleaseTimePeriodSql, params,
                new CarWithProducerNameRowMapper());
    }

    @Override
    public CarWithProducerName getCarById(Integer carId) throws DataAccessException {
        LOGGER.debug("getCarById({})", carId);
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_car_id", carId);
        return namedParameterJdbcTemplate.queryForObject(
                getCarByIdSql, namedParameters, new CarWithProducerNameRowMapper());
    }

    @Override
    public CarWithProducerName getCarByModel(String model) throws DataAccessException {
        LOGGER.debug("getCarByModel({})", model);
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_model", model);
        return namedParameterJdbcTemplate.queryForObject(
                getCarByModelSql, namedParameters, new CarWithProducerNameRowMapper());
    }

    @Override
    public Integer addCar(Car car) throws DataAccessException {
        LOGGER.debug("addCar({})", car.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("p_car_id", car.getCarId());
        params.addValue("p_model", car.getModel());
        params.addValue("p_release_date", car.getReleaseDate());
        params.addValue("p_amount", car.getAmount());
        params.addValue("p_producer_id", car.getProducerId());
        namedParameterJdbcTemplate.update(insertCarSql, params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer updateCar(Car car) throws DataAccessException {
        LOGGER.debug("updateCar({})", car.toString());
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("p_car_id", car.getCarId());
        params.addValue("p_model", car.getModel());
        params.addValue("p_release_date", car.getReleaseDate());
        params.addValue("p_amount", car.getAmount());
        params.addValue("p_producer_id", car.getProducerId());
        return namedParameterJdbcTemplate.update(updateCarSql, params);
    }

    @Override
    public Integer deleteCar(Integer carId) throws DataAccessException {
        LOGGER.debug("deleteCar({})", carId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("p_car_id", carId);
        return namedParameterJdbcTemplate.update(deleteCarSql, params);
    }


    private class CarRowMapper implements RowMapper<Car> {

        @Override
        public Car mapRow(ResultSet resultSet, int i) throws SQLException {
            Car car = new Car(
                    resultSet.getInt(CAR_ID),
                    resultSet.getString(MODEL),
                    resultSet.getDate(RELEASE_DATE),
                    resultSet.getInt(AMOUNT),
                    resultSet.getInt(PRODUCER_ID));
            return car;
        }
    }

    private class CarWithProducerNameRowMapper implements RowMapper<CarWithProducerName> {

        @Override
        public CarWithProducerName mapRow(ResultSet resultSet, int i) throws SQLException {
            CarWithProducerName car = new CarWithProducerName(
                    resultSet.getInt(CAR_ID),
                    resultSet.getString(MODEL),
                    resultSet.getDate(RELEASE_DATE),
                    resultSet.getInt(AMOUNT),
                    resultSet.getInt(PRODUCER_ID),
                    resultSet.getString(PRODUCER_NAME));
            return car;
        }
    }
}
