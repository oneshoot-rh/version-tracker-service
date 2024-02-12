package com.oneshoot.versiontrackerservice.resources;


import com.oneshoot.versiontrackerservice.services.ServicesVersioningService;
import com.oneshoot.versiontrackerservice.utils.enums.DeploymentEnv;
import com.oneshoot.versiontrackerservice.utils.enums.VersionUpdateRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/versioning")
@RequiredArgsConstructor
public class ServicesVersioningResource {

    private final ServicesVersioningService servicesVersioningService;

    @GetMapping("/{serviceName}/{deploymentEnv}")
    public ResponseEntity<String> getDeploymentVersion(@PathVariable @NotBlank String serviceName, @PathVariable @NotBlank DeploymentEnv deploymentEnv){
        return ResponseEntity.ok(servicesVersioningService.getVersionNumber(serviceName,deploymentEnv));
    }

    @PostMapping("/{serviceName}/{deploymentEnv}")
    public ResponseEntity<String> updateDeploymentEnv(@RequestBody VersionUpdateRequest versionUpdateRequest){
        return ResponseEntity.ok(servicesVersioningService.updateVersionNumber(versionUpdateRequest));
    }

}
