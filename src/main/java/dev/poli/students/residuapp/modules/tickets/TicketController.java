package dev.poli.students.residuapp.modules.tickets;

import dev.poli.students.residuapp.modules.tickets.dao.TicketReportRepository;
import dev.poli.students.residuapp.modules.tickets.dao.TicketRepository;
import dev.poli.students.residuapp.modules.tickets.entity.TicketReport;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class TicketController {

    private final TicketReportRepository ticketReportRepository;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;

    @PostMapping("report")
    public void downloadReport(HttpServletResponse response) throws IOException {
        byte[] report = ticketService.generateReport(ticketRepository.findAll());
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmssSSS"));

        String fileName = "ReporteTickets_" + today + ".csv";

        ticketReportRepository.saveAndFlush(TicketReport.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(Instant.now())
                .name(fileName)
                .requestorUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .build());

        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        try (OutputStream outputStream = response.getOutputStream();
             InputStream inputStream = new ByteArrayInputStream(report)) {
            inputStream.transferTo(outputStream);
            response.flushBuffer();
        }
    }
}
