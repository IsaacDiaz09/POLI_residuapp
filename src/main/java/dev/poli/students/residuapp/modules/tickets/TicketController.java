package dev.poli.students.residuapp.modules.tickets;

import dev.poli.students.residuapp.modules.AuthUtils;
import dev.poli.students.residuapp.modules.tickets.dao.TicketReportRepository;
import dev.poli.students.residuapp.modules.tickets.dao.TicketRepository;
import dev.poli.students.residuapp.modules.tickets.dto.TicketDto;
import dev.poli.students.residuapp.modules.tickets.entity.Ticket;
import dev.poli.students.residuapp.modules.tickets.entity.TicketReport;
import dev.poli.students.residuapp.modules.user.dao.UserRepository;
import dev.poli.students.residuapp.modules.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TicketController {

    private final TicketReportRepository ticketReportRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketService ticketService;

    @GetMapping("garbageCollect")
    public ModelAndView ticketView(@RequestParam(required = false) String ticketId, ModelAndView modelAndView) {
        Ticket ticket = null;
        if (StringUtils.hasText(ticketId)) {
            ticket = ticketRepository.findById(ticketId).orElse(null);
            modelAndView.addObject("ticket", ticket);
        }
        modelAndView.addObject("garbageType", ticket == null ? "" : ticket.getGarbageType().name());
        modelAndView.setViewName("ticket");
        return modelAndView;
    }

    @PostMapping(path = "garbageCollect", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void saveTicket(@RequestParam(required = false) String ticketId, TicketDto createTicketDto, HttpServletResponse response) throws IOException {
        String userEmail = AuthUtils.getAuthenticatedUserName();
        User user = userRepository.findByEmail(userEmail);

        if (StringUtils.hasText(ticketId)) {
            // Update ticket
            Optional<Ticket> ticket = ticketRepository.findById(ticketId);
            ticket.ifPresent(t -> {
                if (t.getStatus() == Ticket.Status.PENDING) {
                    t.setStatus(Ticket.Status.PROCESSING);
                    t.setCollectionCompanyId(user.getCompanyId());
                } else {
                    t.setStatus(Ticket.Status.FINISHED);
                    t.setGarbageCollectedKg(createTicketDto.garbageCollectedKg());
                }
                t.setUpdatedAt(Instant.now());
                ticketRepository.saveAndFlush(t);
                log.info("update ticket with ID: {}", t.getId());
            });
        } else {
            // Create Ticket
            Ticket ticket = ticketRepository.saveAndFlush(Ticket.builder()
                    .collectionCompanyId(null)
                    .createdAt(Instant.now())
                    .id(UUID.randomUUID().toString())
                    .requestorUserId(userEmail)
                    .garbageType(createTicketDto.garbageType())
                    .status(Ticket.Status.PENDING)
                    .requestedLocation(createTicketDto.requestedLocation())
                    .build());
            log.info("saved ticket with ID: {}", ticket.getId());
        }
        response.sendRedirect("/poliresiduapp/tickets");
    }

    @PostMapping("/report/tickets")
    public void downloadReport(HttpServletResponse response) throws IOException {
        byte[] report = ticketService.generateReport(findTickets());
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

    private List<Ticket> findTickets() {
        User.Role userRole = AuthUtils.getAuthenticatedUserRole();
        String userEmail = AuthUtils.getAuthenticatedUserName();
        if (userRole == User.Role.SYSTEM_ADMIN) {
            return ticketRepository.findAll();
        } else if (userRole == User.Role.COMPANY_ADMIN) {
            String companyId = userRepository.findByEmail(userEmail).getCompanyId();
            return ticketRepository.findAllByCollectionCompanyId(companyId, Pageable.unpaged());
        }
        return ticketRepository.findAllByRequestorUserId(userEmail, Pageable.unpaged());
    }
}
