package dev.poli.students.residuapp.modules.tickets.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Table
@EqualsAndHashCode
public class TicketReport {

    @Id
    private UUID id;

    private String name;

    private UUID requestorCompanyId;

    private UUID requestorUserId;

    private Instant createdAt;

    private String jsonFilters;
}
