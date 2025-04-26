package dev.poli.students.residuapp.modules.tickets.dao;

import dev.poli.students.residuapp.modules.tickets.entity.TicketReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketReportRepository extends JpaRepository<TicketReport, String> {
}
