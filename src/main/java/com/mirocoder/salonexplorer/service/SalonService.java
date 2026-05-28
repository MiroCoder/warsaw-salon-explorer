package com.mirocoder.salonexplorer.service;

import com.mirocoder.salonexplorer.model.Salon;
import com.mirocoder.salonexplorer.repository.SalonRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SalonService {

    private final SalonRepository salonRepository;

    public SalonService(SalonRepository salonRepository) {
        this.salonRepository = salonRepository;
    }

    public List<Salon> getAllSalons() {
        return salonRepository.findAll();
    }

    public Optional<Salon>  getSalonById(Long id){
        return salonRepository.findById(id);
    }

    public Salon saveSalon(Salon salon){
        return salonRepository.save(salon);
    }

    public Optional<Salon> updateSalon(Long id, Salon updatedSalon) {
        return salonRepository.findById(id)
                .map(existingSalon -> {
                    existingSalon.setName(updatedSalon.getName());
                    existingSalon.setAddress(updatedSalon.getAddress());
                    existingSalon.setDistrict(updatedSalon.getDistrict());
                    existingSalon.setPhone(updatedSalon.getPhone());
                    existingSalon.setWebsite(updatedSalon.getWebsite());
                    existingSalon.setServices(updatedSalon.getServices());
                    existingSalon.setPriceRange(updatedSalon.getPriceRange());
                    existingSalon.setRating(updatedSalon.getRating());
                    existingSalon.setReviewCount(updatedSalon.getReviewCount());

                    return salonRepository.save(existingSalon);
                });
    }

    public List<Salon> getSalonsByDistrict(String district) {
        return salonRepository.findByDistrictIgnoreCase(district);
    }
}
