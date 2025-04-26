package dev.poli.students.residuapp.modules.tickets.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@Entity
@Builder
@Getter
@Table
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TicketReport {

    @Id
    private String id;

    private String name;

    private String requestorCompanyId;

    private String requestorUserId;

    @Getter(AccessLevel.NONE)
    private Instant createdAt;

    private String jsonFilters;


    public ZonedDateTime getCreatedAt() {
        return createdAt.atZone(ZoneId.of(TimeZone.getDefault().getID()));
    }
}
