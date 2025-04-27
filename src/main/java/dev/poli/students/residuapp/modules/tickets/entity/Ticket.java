package dev.poli.students.residuapp.modules.tickets.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@Entity
@Setter
@Getter
@Builder
@Table
@NoArgsConstructor
@AllArgsConstructor
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

    @Getter(AccessLevel.NONE)
    private Instant createdAt;

    private Instant updatedAt;

    private String requestorUserId;

    private String collectionCompanyId;

    @Column(name = "garbageCollectedKg")
    private Integer garbageCollectedKg;

    @Enumerated(EnumType.STRING)
    private Status status;

    public ZonedDateTime getCreatedAt() {
        return createdAt.atZone(ZoneId.of(TimeZone.getDefault().getID()));
    }
}
