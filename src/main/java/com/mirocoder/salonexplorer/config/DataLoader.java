package com.mirocoder.salonexplorer.config;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import com.mirocoder.salonexplorer.model.Salon;
import com.mirocoder.salonexplorer.repository.SalonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final SalonRepository salonRepository;
    private final ObjectMapper objectMapper;

    public DataLoader(SalonRepository salonRepository, ObjectMapper objectMapper) {
        this.salonRepository = salonRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (salonRepository.count() > 0) return;
        InputStream inputStream = new ClassPathResource("data/salons.json").getInputStream();
        List<Salon> salons = objectMapper.readValue(inputStream, new TypeReference<List<Salon>>() {});
        salonRepository.saveAll(salons);
    }
}