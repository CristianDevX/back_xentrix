package com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters.bd1;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "events")
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "location")
    private String location;

    @Column(name = "event_opening_date")
    private Timestamp eventOpeningDate;

    @Column(name = "event_closing_date")
    private Timestamp eventClosingDate;

    public Events() {
    }

    public Events(Integer id, String name, String type, Integer capacity, String location, Timestamp eventOpeningDate,
            Timestamp eventClosingDate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.location = location;
        this.eventOpeningDate = eventOpeningDate;
        this.eventClosingDate = eventClosingDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getEventOpeningDate() {
        return eventOpeningDate;
    }

    public void setEventOpeningDate(Timestamp eventOpeningDate) {
        this.eventOpeningDate = eventOpeningDate;
    }

    public Timestamp getEventClosingDate() {
        return eventClosingDate;
    }

    public void setEventClosingDate(Timestamp eventClosingDate) {
        this.eventClosingDate = eventClosingDate;
    }

}
