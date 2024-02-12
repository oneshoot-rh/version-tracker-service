package com.oneshoot.versiontrackerservice;

import com.oneshoot.versiontrackerservice.entities.ServiceVersioningEntity;
import com.oneshoot.versiontrackerservice.repositories.ServicesVersioningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class VersionTrackerServiceApplication {

    @Autowired
    ServicesVersioningRepository servicesVersioningRepository;

    public static void main(String[] args) {
        SpringApplication.run(VersionTrackerServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner runner(){
        return args ->{
            List<ServiceVersioningEntity> versioningEntities = List.of(
                    new ServiceVersioningEntity(1L,"oneshootmain","1.1.0","1.0.0", LocalDateTime.now()),
                    new ServiceVersioningEntity(2L,"resumeParser","1.2.0","1.1.0", LocalDateTime.now())
            );
            servicesVersioningRepository.saveAll(versioningEntities);
        };
    }
}
