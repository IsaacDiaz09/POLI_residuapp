package dev.poli.students.residuapp.modules.tickets.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;

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
    private String id;

    private String requestedLocation;

    @Enumerated(EnumType.STRING)
    private GarbageType garbageType;

    private Instant createdAt;

    private Instant updatedAt;

    private String requestorUserId;

    private String collectionCompanyId;

    @Enumerated(EnumType.STRING)
    private Status status;
}
