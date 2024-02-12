package com.oneshoot.versiontrackerservice.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_versioning_entities")
@Builder
@Setter
@Getter
@AllArgsConstructor @NoArgsConstructor
public class ServiceVersioningEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    String serviceName;
    String devVersion;
    String prodVersion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime lastUpdate;
}
