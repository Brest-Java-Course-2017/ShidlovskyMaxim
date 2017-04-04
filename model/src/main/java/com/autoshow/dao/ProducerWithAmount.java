package com.autoshow.dao;

import java.util.Objects;

/**
 * Created by maxim on 4.4.17.
 */
public class ProducerWithAmount {

    private Integer producerId;
    private String name;
    private String country;
    private Integer amountOfCars;

    public ProducerWithAmount() { }

    public ProducerWithAmount(Integer producerId, String name, String country, Integer amountOfCars) {
        this.producerId = producerId;
        this.name = name;
        this.country = country;
        this.amountOfCars = amountOfCars;
    }

    public Integer getProducerId() {
        return producerId;
    }

    public void setProducerId(Integer producerId) {
        this.producerId = producerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getAmountOfCars() {
        return amountOfCars;
    }

    public void setAmountOfCars(Integer amountOfCars) {
        this.amountOfCars = amountOfCars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProducerWithAmount producer = (ProducerWithAmount) o;
        return Objects.equals(producerId, producer.producerId) &&
                Objects.equals(name, producer.name) &&
                Objects.equals(country, producer.country) &&
                Objects.equals(amountOfCars, producer.amountOfCars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producerId, name, country, amountOfCars);
    }

    @Override
    public String toString() {
        return "ProducerWithAmount{" +
                "producerId=" + producerId +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", amountOfCars=" + amountOfCars +
                '}';
    }
}
