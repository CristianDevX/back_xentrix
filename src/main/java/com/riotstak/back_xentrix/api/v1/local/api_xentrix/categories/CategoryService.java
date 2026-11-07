package com.riotstak.back_xentrix.api.v1.local.api_xentrix.categories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.categories.adapters.CategoryAdapter;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.categories.adapters.bd1.Category;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.EventService;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters.payloads.EventDto;
import com.riotstak.back_xentrix.api.v1.local.utils.ErrorService;

@Service
public class CategoryService {

    private final String myClassName = EventService.class.getName();

    @Autowired
    CategoryAdapter categoryAdapter;

    public Object getAll() {
        try {
            return categoryAdapter.getAll();
        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error consultando todos las categorias", myClassName);
        }
    }

    public Object getById(Long id) {
        try {
            Optional<?> eventResp = (Optional<?>) categoryAdapter.getById(id);

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
                Category category = new Category();
                //
                // Crear categoria
                //
                Object id = categoryAdapter.create(category);
                return "fue creado de forma exitosa el evento con id " + id;
            }
            return new ErrorService("Los datos name, type, capaity, location son requeridos",
                    "Datos inconcistentes para crear el evento ", myClassName);

        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error creando el evento ", myClassName);
        }
    }

}
