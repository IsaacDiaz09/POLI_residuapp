package dev.poli.students.residuapp.modules.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

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
    @Builder.Default
    private Long id = System.nanoTime();

    private String name;

    @Builder.Default
    private Instant createdAt = Instant.now();

    private Instant modifiedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    // this field may be null if user does not belong to a company
    private UUID companyId;
}
