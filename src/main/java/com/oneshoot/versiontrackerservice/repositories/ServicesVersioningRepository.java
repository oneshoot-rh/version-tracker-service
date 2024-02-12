package com.oneshoot.versiontrackerservice.repositories;

import com.oneshoot.versiontrackerservice.entities.ServiceVersioningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicesVersioningRepository extends JpaRepository<ServiceVersioningEntity,Long> {
    Optional<ServiceVersioningEntity> findByServiceName(String serviceName);
}
