package com.oneshoot.versiontrackerservice.resources;


import com.oneshoot.versiontrackerservice.services.ServicesVersioningService;
import com.oneshoot.versiontrackerservice.utils.enums.DeploymentEnv;
import com.oneshoot.versiontrackerservice.utils.enums.VersionUpdateRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/versioning")
@RequiredArgsConstructor
@Slf4j
public class ServicesVersioningResource {

    private final ServicesVersioningService servicesVersioningService;

    @GetMapping("/{serviceName}/{deploymentEnv}")
    public ResponseEntity<String> getDeploymentVersion(@PathVariable @NotBlank String serviceName, @PathVariable @NotBlank String deploymentEnv){
        String versionNumber = servicesVersioningService.getVersionNumber(serviceName, DeploymentEnv.valueOf(deploymentEnv));
        log.info("Version Number for Service {} in {} Environment is {}",serviceName,deploymentEnv,versionNumber);
        return ResponseEntity.ok(versionNumber);
    }

    @PostMapping
    public ResponseEntity<String> updateDeploymentEnv(@RequestBody VersionUpdateRequest versionUpdateRequest){
        String updatedVersionNumber = servicesVersioningService.updateVersionNumber(versionUpdateRequest);
        log.info("Version Number for Service {} in {} Environment is updated to {}",versionUpdateRequest.serviceName(),versionUpdateRequest.deploymentEnv(),updatedVersionNumber);
        return ResponseEntity.ok(updatedVersionNumber);
    }

}
