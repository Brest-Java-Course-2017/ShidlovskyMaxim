package com.autoshow.dao;

import java.util.Date;
import java.util.Objects;

/**
 * Model for particular car.
 *
 * Created by maxim on 5.3.17.
 */
public class Car {

    private Integer carId;
    private String model;
    private Date releaseDate;
    private Integer amount;

    public Car() { }

    public Car(Integer carId, String model, Date releaseDate, Integer amount) {
        this.carId = carId;
        this.model = model;
        this.releaseDate = releaseDate;
        this.amount = amount;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        Car car = (Car) o;
        return Objects.equals(this.carId, car.getCarId())
                && Objects.equals(this.model, car.getModel())
                && Objects.equals(this.releaseDate, car.getReleaseDate())
                && Objects.equals(this.amount, car.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.carId, this.model, this.releaseDate, this.amount);
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", dao='" + model + '\'' +
                ", releaseDate=" + releaseDate +
                ", amount=" + amount +
                '}';
    }
}
