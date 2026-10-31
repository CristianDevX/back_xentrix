package com.riotstak.back_xentrix.api.v1.local.api_xentrix.events;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters.EventAdapter;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters.bd1.Event;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters.payloads.EventDto;
import com.riotstak.back_xentrix.api.v1.local.utils.ErrorService;

@Service
public class EventService {

    private final String myClassName = EventService.class.getName();

    EventAdapter eventsAdapter;

    public Object getAll() {
        try {
            return eventsAdapter.getAll();
        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error consultando todos los eventos", myClassName);
        }
    }

    public Object getById(Integer id) {
        try {
            Optional<?> eventResp = (Optional<?>) eventsAdapter.getById(id);

            if (eventResp.isEmpty()) {
                return "evento no encontrado";
            } else {
                return eventResp;
            }
        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error consultando el evento con uid " + id, myClassName);
        }
    }

    public Object create(EventDto payload) {
        try {
            if (payload.getName() != null && payload.getType() != null && payload.getCapacity() != null
                    && payload.getLocation() != null) {
                Event event = new Event();
                // Crear evento
                // event.setName(payload.getName());
                // event.setType(payload.getType());
                // event.setCapacity(payload.getCapacity());
                // event.setLocation(payload.getLocation());
                Object id = eventsAdapter.create(event);
                return "fue creado de forma exitosa el evento con id " + id;
            }
            return new ErrorService("Los datos name, type, capaity, location son requeridos",
                    "Datos inconcistentes para crear el evento ", myClassName);

        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error creando el evento ", myClassName);
        }
    }
}
