package com.riotstak.back_xentrix.api.v1.local.api_xentrix.events;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters.payloads.EventDto;
import com.riotstak.back_xentrix.api.v1.local.utils.ResponseLocal;

import javax.servlet.http.HttpServletRequest;

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters.bd1.Event;

@RestController
@RequestMapping("/local/events")
public class EventController {

    @Autowired
    EventService eventsService;

    private final String myClassName = EventController.class.getName();

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll(HttpServletRequest http) {

        try {
            List<Object> x = (List<Object>) eventsService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(x);

        } catch (Exception e) {
            return new ResponseEntity<>(myClassName + " - " + e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest http) {
        try {
            Object x = eventsService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(x);
        } catch (Exception e) {
            return new ResponseEntity<>(myClassName + " - " + e.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody EventDto payload, HttpServletRequest req,
            BindingResult bindingResult) {
        ResponseLocal response = new ResponseLocal(null, myClassName);
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Object x = eventsService.create(payload);
            return new ResponseEntity<>(x, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(myClassName + " - " + e.getMessage(), HttpStatus.OK);
        }
    }
}
