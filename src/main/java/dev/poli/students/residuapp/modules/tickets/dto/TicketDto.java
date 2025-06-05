package dev.poli.students.residuapp.modules.tickets.dto;

import dev.poli.students.residuapp.modules.tickets.entity.Ticket;

public record TicketDto(String requestedLocation, Ticket.GarbageType garbageType, Integer garbageCollectedKg) {
}
