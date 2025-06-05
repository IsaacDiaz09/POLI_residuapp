package dev.poli.students.residuapp.modules.tickets.dao;

import dev.poli.students.residuapp.modules.tickets.entity.TicketReport;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketReportRepository extends JpaRepository<TicketReport, String> {
    List<TicketReport> findAllByRequestorUserId(String requestorUserId, Pageable pageable);
}
