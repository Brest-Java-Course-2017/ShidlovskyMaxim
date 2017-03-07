package com.autoshow.dao;

import java.util.Objects;

/**
 * Model for producer of a car.
 *
 * Created by maxim on 5.3.17.
 */
public class Producer {

    private Integer producerId;
    private String name;
    private String country;

    public Producer() { }

    public Producer(Integer producerId, String name, String country) {
        this.producerId = producerId;
        this.name = name;
        this.country = country;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (this == null || this.getClass() != o.getClass())
            return false;
        Producer producer = (Producer) o;
        return Objects.equals(this.producerId, producer.getProducerId())
                && Objects.equals(this.name, producer.getName())
                && Objects.equals(this.country, producer.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.producerId, this.name, this.country);
    }

    @Override
    public String toString() {
        return "Producer{" +
                "producerId=" + producerId +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
