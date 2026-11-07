package com.riotstak.back_xentrix.api.v1.local.api_xentrix.venues.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.venues.adapters.bd1.Venue;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.venues.adapters.bd1.VenueRepository;
import com.riotstak.back_xentrix.api.v1.local.utils.ErrorService;

@Service
public class VenueAdapter {

    private final String myClassName = VenueAdapter.class.getName();

    @Autowired
    VenueRepository venueRepository;

    public Object getAll() {
        try {
            return venueRepository.findAll();
        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error consultando todos los lugares para enventos", myClassName);
        }
    }

    public Object getById(Long id) {
        try {
            return venueRepository.findById(id);
        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error consultando el lugar para evento con id " + id, myClassName);
        }
    }

    public Object create(Venue venue) {
        try {
            return venueRepository.save(venue).getId();
        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error creando el lugar del evento ", myClassName);
        }
    }

}
