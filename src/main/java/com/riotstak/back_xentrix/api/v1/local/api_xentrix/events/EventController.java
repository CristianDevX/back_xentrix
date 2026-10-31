package com.riotstak.back_xentrix.api.v1.local.api_xentrix.events;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.events.adapters.payloads.EventDto;

@RestController
@RequestMapping("/local/events")
public class EventController {

    EventService eventsService;

    private final String myClassName = EventController.class.getName();

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll(HttpServletRequest http) {
        try {
            Object x = eventsService.getAll();
            return new ResponseEntity<>(x, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(myClassName + " - " + e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id, HttpServletRequest http) {
        try {
            Object x = eventsService.getById(id);
            return new ResponseEntity<>(x, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(myClassName + " - " + e.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody EventDto payload, HttpServletRequest http) {
        try {
            Object x = eventsService.create(payload);
            return new ResponseEntity<>(x, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(myClassName + " - " + e.getMessage(), HttpStatus.OK);
        }
    }
}
