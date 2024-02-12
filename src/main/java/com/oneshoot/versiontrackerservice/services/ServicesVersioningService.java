package com.oneshoot.versiontrackerservice.services;

import com.oneshoot.versiontrackerservice.entities.ServiceVersioningEntity;
import com.oneshoot.versiontrackerservice.repositories.ServicesVersioningRepository;
import com.oneshoot.versiontrackerservice.utils.enums.DeploymentEnv;
import com.oneshoot.versiontrackerservice.utils.enums.VersionUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicesVersioningService {


    private final ServicesVersioningRepository servicesVersioningRepository;

    public String getVersionNumber(String serviceName, DeploymentEnv deploymentEnv) {
        Optional<ServiceVersioningEntity> versioningEntityOptional = servicesVersioningRepository.findByServiceName(serviceName);
        if (versioningEntityOptional.isEmpty()) throw new RuntimeException("No Registered Service Found With Name "+serviceName+ " in Version Tracker Record");
        else {
            ServiceVersioningEntity serviceVersioningEntity = versioningEntityOptional.get();
            if (deploymentEnv.equals(DeploymentEnv.DEV)) return serviceVersioningEntity.getDevVersion();
            else return serviceVersioningEntity.getProdVersion();
        }
    }

    public String updateVersionNumber(VersionUpdateRequest versionUpdateRequest) {
        Optional<ServiceVersioningEntity> versioningEntityOptional = servicesVersioningRepository.findByServiceName(versionUpdateRequest.serviceName());
        if (versioningEntityOptional.isEmpty()) throw new RuntimeException("No Registered Service Found With Name "+versionUpdateRequest.serviceName()+ " in Version Tracker Record");
        return null;
    }
}
