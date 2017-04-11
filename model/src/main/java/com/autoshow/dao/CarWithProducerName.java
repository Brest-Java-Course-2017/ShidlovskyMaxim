package com.autoshow.dao;

import java.util.Date;
import java.util.Objects;

/**
 * Created by maxim on 11.4.17.
 */
public class CarWithProducerName {

    private Integer carId;
    private String model;
    private Date releaseDate;
    private Integer amount;
    private Integer producerId;
    private String producerName;

    public CarWithProducerName() { }

    public CarWithProducerName(Integer carId, String model, Date releaseDate, Integer amount,
                               Integer producerId, String producerName) {
        this.carId = carId;
        this.model = model;
        this.releaseDate = releaseDate;
        this.amount = amount;
        this.producerId = producerId;
        this.producerName = producerName;
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

    public Integer getProducerId() {
        return producerId;
    }

    public void setProducerId(Integer producerId) {
        this.producerId = producerId;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        CarWithProducerName car = (CarWithProducerName) o;
        return Objects.equals(this.carId, car.getCarId())
                && Objects.equals(this.model, car.getModel())
                && Objects.equals(this.releaseDate, car.getReleaseDate())
                && Objects.equals(this.amount, car.getAmount())
                && Objects.equals(this.producerId, car.getProducerId())
                && Objects.equals(this.producerName, car.getProducerName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.carId, this.model, this.releaseDate, this.amount,
                this.producerId, this.producerName);
    }

    @Override
    public String toString() {
        return "CarWithProducerName{" +
                "carId=" + carId +
                ", model='" + model + '\'' +
                ", releaseDate=" + releaseDate +
                ", amount=" + amount +
                ", producerId=" + producerId +
                ", producerName='" + producerName + '\'' +
                '}';
    }
}
