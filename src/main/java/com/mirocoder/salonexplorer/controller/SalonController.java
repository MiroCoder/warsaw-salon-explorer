package com.mirocoder.salonexplorer.controller;

import com.mirocoder.salonexplorer.model.Salon;
import com.mirocoder.salonexplorer.service.SalonService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequestMapping("/api/salons")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class SalonController {

    private final SalonService salonService;

    public SalonController(SalonService salonService) {
        this.salonService = salonService;
    }

//    @GetMapping
//    public List<Salon> getAllSalons() {
//        return salonService.getAllSalons();
//    }

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

    @PutMapping("/{id}")
    public ResponseEntity<Salon> updateSalon(@PathVariable Long id, @RequestBody Salon salon) {
        return salonService.updateSalon(id, salon)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Salon> getSalons(@RequestParam(required = false) String district) {
        if (district != null && !district.isBlank()) {
            return salonService.getSalonsByDistrict(district);
        }
        return salonService.getAllSalons();
    }
}