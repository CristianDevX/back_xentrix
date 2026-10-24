package com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters.bd1.Events;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters.bd1.EventsRepository;
import com.riotstak.back_xentrix.api.v1.local.utils.ErrorService;

@Service
public class EventsAdapter {

    private final String myClassName = EventsAdapter.class.getName();

    @Autowired
    EventsRepository eventsRepository;

    public Object getAll() {
        try {
            return eventsRepository.findAll();
        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error consultando todos los eventos", myClassName);
        }
    }

    public Object getById(Integer id) {
        try {
            return eventsRepository.findById(id);
        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error consultando el evento con id " + id, myClassName);
        }
    }

    public Object create(Events event) {
        try {
            return eventsRepository.save(event).getId();
        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error creando el evento ", myClassName);
        }
    }
}
