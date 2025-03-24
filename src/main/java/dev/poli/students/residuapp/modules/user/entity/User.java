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
public class User {

    public enum Status {
        ENABLED,
        DISABLED,
        BLOCKED,
    }

    public enum Role {
        UNDEFINED,
        SYSTEM_ADMIN,
        COMPANY_ADMIN,
    }

    @Id
    private Long id;

    private String name;

    private Instant createdAt;

    private Instant modifiedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    // this field may be null if user does not belong to a company
    private UUID companyId;
}
