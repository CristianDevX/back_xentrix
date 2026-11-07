package com.riotstak.back_xentrix.api.v1.local.api_xentrix.venues;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.categories.adapters.bd1.Category;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.EventService;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.venues.adapters.VenueAdapter;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.venues.adapters.bd1.Venue;
import com.riotstak.back_xentrix.api.v1.local.utils.ErrorService;

@Service
public class VenueService {

    private final String myClassName = EventService.class.getName();

    @Autowired
    VenueAdapter venueAdapter;

    public Object getAll() {
        try {
            return venueAdapter.getAll();
        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error consultando todos los lugares para evento", myClassName);
        }
    }

    public Object getById(Long id) {
        try {
            Optional<?> eventResp = (Optional<?>) venueAdapter.getById(id);

            if (eventResp.isEmpty()) {
                return "evento no encontrado";
            } else {
                return eventResp;
            }
        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error consultando el lugar para evento con uid " + id,
                    myClassName);
        }
    }

    public Object create(Venue payload) {
        try {
            if (payload.getName() != null) {
                Category category = new Category();
                //
                // Crear categoria
                //
                Object id = venueAdapter.create(payload);
                return "fue creado de forma exitosa el evento con id " + id;
            }
            return new ErrorService("Los datos name, type, capaity, location son requeridos",
                    "Datos inconcistentes para crear el evento ", myClassName);

        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error creando el lugar para evento ", myClassName);
        }
    }

}
