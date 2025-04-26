package dev.poli.students.residuapp.modules.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@Entity
@Getter
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private String id;

    private String name;

    @Column
    private String email;

    @Builder.Default
    @Getter(AccessLevel.NONE)
    private Instant createdAt = Instant.now();

    private Instant modifiedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    // Puede ser nulo si no pertenece a ninguna empresa
    private String companyId;

    public ZonedDateTime getCreatedAt() {
        return createdAt.atZone(ZoneId.of(TimeZone.getDefault().getID()));
    }
}
