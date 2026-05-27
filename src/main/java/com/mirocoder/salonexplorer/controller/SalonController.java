package com.mirocoder.salonexplorer.controller;

import com.mirocoder.salonexplorer.model.Salon;
import com.mirocoder.salonexplorer.service.SalonService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
public class SalonController {

    private final SalonService salonService;

    public SalonController(SalonService salonService) {
        this.salonService = salonService;
    }

    @GetMapping
    public List<Salon> getAllSalons() {
        return salonService.getAllSalons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salon> getSalonById(@PathVariable Long id) {
        return salonService.getSalonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Salon createSalon(@RequestBody Salon salon) {
        return salonService.saveSalon(salon);
    }
}