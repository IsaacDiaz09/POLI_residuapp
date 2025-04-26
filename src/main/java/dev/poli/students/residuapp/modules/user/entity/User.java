package dev.poli.students.residuapp.modules.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

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

    // 🚀 AQUÍ AÑADES EL CAMPO EMAIL
    @Column
    private String email;

    @Builder.Default
    private Instant createdAt = Instant.now();

    private Instant modifiedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    // Puede ser nulo si no pertenece a ninguna empresa
    private String companyId;
}
