package com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters.bd1.Event;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters.bd1.EventRepository;
import com.riotstak.back_xentrix.api.v1.local.utils.ErrorService;

@Service
public class EventAdapter {

    private final String myClassName = EventAdapter.class.getName();

    @Autowired
    EventRepository eventsRepository;

    public Object getAll() {
        try {
            return eventsRepository.findAll();
        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error consultando todos los eventos", myClassName);
        }
    }

    public Object getById(Long id) {
        try {
            return eventsRepository.findById(id);
        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error consultando el evento con id " + id, myClassName);
        }
    }

    public Object create(Event event) {
        try {
            return eventsRepository.save(event).getId();
        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error creando el evento ", myClassName);
        }
    }
}

// ajustar el response local para todos los servicios
// Ajustar registro de log en bd y en archivo local
