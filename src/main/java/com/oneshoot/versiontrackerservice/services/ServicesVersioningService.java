package com.oneshoot.versiontrackerservice.services;

import com.oneshoot.versiontrackerservice.entities.ServiceVersioningEntity;
import com.oneshoot.versiontrackerservice.repositories.ServicesVersioningRepository;
import com.oneshoot.versiontrackerservice.utils.enums.DeploymentEnv;
import com.oneshoot.versiontrackerservice.utils.enums.VersionPart;
import com.oneshoot.versiontrackerservice.utils.enums.VersionUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String currentVersion;
        ServiceVersioningEntity versioningEntity = versioningEntityOptional.get();
        if (DeploymentEnv.valueOf(versionUpdateRequest.deploymentEnv()) == DeploymentEnv.DEV) {
            currentVersion = versioningEntity.getDevVersion();
        } else {
            currentVersion = versioningEntity.getProdVersion();
        }
        String updatedVersion = incrementVersion(currentVersion, VersionPart.valueOf(versionUpdateRequest.versionPart()));

        if (DeploymentEnv.valueOf(versionUpdateRequest.deploymentEnv()) == DeploymentEnv.DEV) {
            versioningEntity.setDevVersion(updatedVersion);
        } else {
            versioningEntity.setProdVersion(updatedVersion);
        }
        versioningEntity.setLastUpdate(LocalDateTime.now());
        servicesVersioningRepository.save(versioningEntity);
        return updatedVersion;
    }

    private int[] splitVersion(String version) {
        Pattern pattern = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)");
        Matcher matcher = pattern.matcher(version);

        if (matcher.matches()) {
            int major = Integer.parseInt(matcher.group(1));
            int minor = Integer.parseInt(matcher.group(2));
            int patch = Integer.parseInt(matcher.group(3));

            return new int[]{major,minor,patch};
        } else {
            throw new IllegalArgumentException("Invalid version format: " + version);
        }
    }

    private String incrementVersion(String currentVersion, VersionPart versionPart) {
        int[] versionParts = splitVersion(currentVersion);
        switch (versionPart) {
            case MAJOR -> versionParts[0]++;
            case MINOR -> versionParts[1]++;
            case PATCH -> versionParts[2]++;
            default -> throw new IllegalArgumentException("Unsupported VersionPart: " + versionPart);
        }
        return versionParts[0]+"."+versionParts[1]+"."+versionParts[2];
    }
}
