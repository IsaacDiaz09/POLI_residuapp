package dev.poli.students.residuapp.modules.user.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Table
@EqualsAndHashCode
public class Company {

    public enum Status {
        ENABLED,
        DISABLED
    }

    @Id
    private UUID id;

    private String identification;

    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    private Status status;
}
