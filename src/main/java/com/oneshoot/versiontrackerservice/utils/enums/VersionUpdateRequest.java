package com.oneshoot.versiontrackerservice.utils.enums;

import jakarta.validation.constraints.NotBlank;

public record VersionUpdateRequest(
        @NotBlank String serviceName,
        @NotBlank DeploymentEnv deploymentEnv,
        @NotBlank VersionPart versionPart
) {
}
