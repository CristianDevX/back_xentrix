package com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters.payloads;

import javax.validation.constraints.NotBlank;

public class EventDto {

    @NotBlank(message = "El atriburo name no puede ser vacio o nulo")
    private String name;
    @NotBlank(message = "El atriburo type no puede ser vacio o nulo")
    private String type;
    @NotBlank(message = "El atriburo capacity no puede ser vacio o nulo")
    private Integer capacity;
    @NotBlank(message = "El atriburo location no puede ser vacio o nulo")
    private String location;

    public EventDto(Integer capacity, String location, String name, String type) {
        this.capacity = capacity;
        this.location = location;
        this.name = name;
        this.type = type;
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
}
