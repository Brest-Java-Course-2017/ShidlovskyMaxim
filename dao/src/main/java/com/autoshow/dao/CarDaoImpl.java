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
import java.sql.Date;
import java.util.List;

/**
 * Car DAO implementation.
 *
 * Created by maxim on 9.3.17.
 */
public class CarDaoImpl implements CarDao {

    private static final Logger LOGGER = LogManager.getLogger();

    static final String CAR_ID = "car_id";
    static final String MODEL = "model";
    static final String RELEASE_DATE = "release_date";
    static final String AMOUNT = "amount";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${car.select}")
    String getAllCarsSql;

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

    @Value("${car.deleteCarFromBindingTable}")
    String deleteCarFromBindingTableSql;

    public CarDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Car> getAllCars() throws DataAccessException {
        LOGGER.debug("getAllCars()");
        return jdbcTemplate.query(getAllCarsSql, new CarRowMapper());
    }

    @Override
    public List<Car> getCarsForReleaseTimePeriod(Date from, Date to) throws DataAccessException {
        LOGGER.debug("getCarsForReleaseTimePeriod(from {} to {})", from.toString(), to.toString());
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("p_from", from);
        params.addValue("p_to", to);
        return namedParameterJdbcTemplate.query(getCarsForReleaseTimePeriodSql, params, new CarRowMapper());
    }

    @Override
    public Car getCarById(Integer carId) throws DataAccessException {
        LOGGER.debug("getCarById({})", carId);
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_car_id", carId);
        Car car = namedParameterJdbcTemplate.queryForObject(
                getCarByIdSql, namedParameters, new CarRowMapper());
        return car;
    }

    @Override
    public Car getCarByModel(String model) throws DataAccessException {
        LOGGER.debug("getCarByModel({})", model);
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_model", model);
        Car car = namedParameterJdbcTemplate.queryForObject(
                getCarByModelSql, namedParameters, new CarRowMapper());
        return car;
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
        return namedParameterJdbcTemplate.update(updateCarSql, params);
    }

    @Override
    public Integer deleteCar(Integer carId) throws DataAccessException {
        LOGGER.debug("deleteCar({})", carId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("p_car_id", carId);
        namedParameterJdbcTemplate.update(deleteCarFromBindingTableSql, params);
        return namedParameterJdbcTemplate.update(deleteCarSql, params);
    }


    private class CarRowMapper implements RowMapper<Car> {

        @Override
        public Car mapRow(ResultSet resultSet, int i) throws SQLException {
            Car car = new Car(
                    resultSet.getInt(CAR_ID),
                    resultSet.getString(MODEL),
                    resultSet.getDate(RELEASE_DATE),
                    resultSet.getInt(AMOUNT));
            return car;
        }
    }
}
