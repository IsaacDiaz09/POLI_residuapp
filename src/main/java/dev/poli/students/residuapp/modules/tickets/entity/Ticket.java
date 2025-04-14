package dev.poli.students.residuapp.modules.tickets.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Table
@EqualsAndHashCode
public class Ticket {

    public enum GarbageType {
        DANGEROUS,
        ORGANIC,
        INORGANIC,
    }

    public enum Status {
        PENDING,
        PROCESSING,
        FINISHED,
    }

    @Id
    private UUID id;

    private String requestedLocation;

    @Enumerated(EnumType.STRING)
    private GarbageType garbageType;

    private Instant createdAt;

    private Instant updatedAt;

    private UUID requestorUserId;

    private UUID collectionCompanyId;

    @Enumerated(EnumType.STRING)
    private Status status;
}
