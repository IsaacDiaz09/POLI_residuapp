package dev.poli.students.residuapp.modules.user.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;

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
    private String id;

    private String identification;

    private String name;

    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    private Status status;
}
