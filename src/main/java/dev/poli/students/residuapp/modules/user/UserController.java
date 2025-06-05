package dev.poli.students.residuapp.modules.user;

import dev.poli.students.residuapp.modules.AuthUtils;
import dev.poli.students.residuapp.modules.auth.FirebaseAuthService;
import dev.poli.students.residuapp.modules.tickets.dao.TicketReportRepository;
import dev.poli.students.residuapp.modules.tickets.dao.TicketRepository;
import dev.poli.students.residuapp.modules.tickets.entity.TicketReport;
import dev.poli.students.residuapp.modules.user.dao.UserRepository;
import dev.poli.students.residuapp.modules.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final TicketReportRepository ticketReportRepository;
    private final TicketRepository ticketRepository;
    private final FirebaseAuthService authService;
    private final UserRepository repository;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/report/users")
    public void downloadReport(HttpServletResponse response) throws IOException {
        byte[] report = userService.generateReport(findUsers());
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmssSSS"));

        String fileName = "ReporteUsuarios_" + today + ".csv";

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

    private List<User> findUsers() {
        User.Role userRole = AuthUtils.getAuthenticatedUserRole();
        String userEmail = AuthUtils.getAuthenticatedUserName();
        if (userRole == User.Role.COMPANY_ADMIN) {
            String companyId = userRepository.findByEmail(userEmail).getCompanyId();
            return userRepository.findByCompanyId(companyId, Pageable.unpaged());
        } else if (userRole == User.Role.SYSTEM_ADMIN) {
            return userRepository.findAll(Pageable.unpaged()).getContent();
        }
        return new ArrayList<>();
    }
}
