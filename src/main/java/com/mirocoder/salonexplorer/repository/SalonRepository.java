package com.mirocoder.salonexplorer.repository;
import com.mirocoder.salonexplorer.model.Salon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalonRepository extends JpaRepository<Salon, Long> {
    List<Salon> findByDistrictIgnoreCase(String district);
}
