package com.mirocoder.salonexplorer.service;

import com.mirocoder.salonexplorer.model.Salon;
import com.mirocoder.salonexplorer.repository.SalonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalonServiceTest {

    @Mock
    private SalonRepository salonRepository;

    @InjectMocks
    private SalonService salonService;

    private Salon salon;

    @BeforeEach
    void setUp() {
        salon = new Salon();
        salon.setId(1L);
        salon.setName("Salon One");
        salon.setDistrict("Mokotow");
    }

    @Test
    void returnsAllSalons() {
        when(salonRepository.findAll()).thenReturn(List.of(salon));

        List<Salon> result = salonService.getAllSalons();

        assertEquals(1, result.size());
        assertEquals("Salon One", result.getFirst().getName());
        verify(salonRepository).findAll();
    }

    @Test
    void returnsSalonById() {
        when(salonRepository.findById(1L)).thenReturn(Optional.of(salon));

        Optional<Salon> result = salonService.getSalonById(1L);

        assertTrue(result.isPresent());
        assertEquals("Salon One", result.orElseThrow().getName());
    }

    @Test
    void returnsEmptyWhenSalonDoesNotExist() {
        when(salonRepository.findById(999L)).thenReturn(Optional.empty());

        assertTrue(salonService.getSalonById(999L).isEmpty());
    }

    @Test
    void filtersSalonsByDistrict() {
        when(salonRepository.findByDistrictIgnoreCase("Mokotow")).thenReturn(List.of(salon));

        List<Salon> result = salonService.getSalonsByDistrict("Mokotow");

        assertEquals(1, result.size());
        verify(salonRepository).findByDistrictIgnoreCase("Mokotow");
    }

    @Test
    void updatesExistingSalon() {
        Salon update = new Salon();
        update.setName("Updated Salon");
        update.setAddress("New Address");
        update.setDistrict("Wola");
        update.setPhone("+48 111 222 333");
        update.setWebsite("https://example.com");
        update.setServices("haircut, coloring");
        update.setPriceRange("100-300 PLN");
        update.setRating(4.9);
        update.setReviewCount(150);

        when(salonRepository.findById(1L)).thenReturn(Optional.of(salon));
        when(salonRepository.save(any(Salon.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Salon result = salonService.updateSalon(1L, update).orElseThrow();

        assertEquals("Updated Salon", result.getName());
        assertEquals("Wola", result.getDistrict());
        assertEquals(4.9, result.getRating());
        verify(salonRepository).save(salon);
    }

    @Test
    void doesNotSaveWhenUpdatingMissingSalon() {
        when(salonRepository.findById(999L)).thenReturn(Optional.empty());

        assertTrue(salonService.updateSalon(999L, new Salon()).isEmpty());
        verify(salonRepository, never()).save(any());
    }
}
