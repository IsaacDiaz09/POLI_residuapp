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
    private String id;

    private String name;

    private String requestorCompanyId;

    private String requestorUserId;

    private Instant createdAt;

    private String jsonFilters;
}
