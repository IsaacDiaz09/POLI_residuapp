package dev.poli.students.residuapp.modules.auth;

import dev.poli.students.residuapp.modules.AuthUtils;
import dev.poli.students.residuapp.modules.tickets.dao.TicketReportRepository;
import dev.poli.students.residuapp.modules.tickets.dao.TicketRepository;
import dev.poli.students.residuapp.modules.tickets.entity.Ticket;
import dev.poli.students.residuapp.modules.tickets.entity.TicketReport;
import dev.poli.students.residuapp.modules.user.dao.UserRepository;
import dev.poli.students.residuapp.modules.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private static final Pageable PAGE = PageRequest.of(0, 7);

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketReportRepository ticketReportRepository;

    @GetMapping("home")
    public ModelAndView home(ModelAndView modelAndView) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("user", authentication);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping("companies")
    public ModelAndView companies(ModelAndView modelAndView) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("user", authentication);
        modelAndView.setViewName("companies");
        return modelAndView;
    }

    @GetMapping("tickets")
    public ModelAndView tickets(ModelAndView modelAndView) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("user", authentication);
        modelAndView.addObject("tickets", getTickets());
        modelAndView.setViewName("tickets");
        return modelAndView;
    }

    @GetMapping("users")
    public ModelAndView users(ModelAndView modelAndView) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("user", authentication);
        modelAndView.addObject("users", userRepository.findRegularUsers());
        modelAndView.setViewName("users");
        return modelAndView;
    }

    @GetMapping("formRegister")
    public ModelAndView showform(ModelAndView modelAndView) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("user", authentication);
        modelAndView.setViewName("form");
        return modelAndView;
    }

    @GetMapping("admin")
    public ModelAndView showadmin(ModelAndView modelAndView) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("user", authentication);
        modelAndView.addObject("collectors", userRepository.findAllGarbageCollectors());
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @GetMapping("report")
    public ModelAndView showReport(ModelAndView modelAndView) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("user", authentication);
        modelAndView.addObject("reports", getTicketReports());
        modelAndView.setViewName("report");
        return modelAndView;
    }

    // util methods
    private List<TicketReport> getTicketReports() {
        String userEmail = AuthUtils.getAuthenticatedUserName();
        return ticketReportRepository.findAllByRequestorUserId(userEmail, PAGE);
    }

    private List<Ticket> getTickets() {
        User.Role userRole = AuthUtils.getAuthenticatedUserRole();
        String userEmail = AuthUtils.getAuthenticatedUserName();
        if (userRole == User.Role.UNDEFINED) {
            return ticketRepository.findAllByRequestorUserId(userEmail, PAGE);
        } else if (userRole == User.Role.COMPANY_ADMIN) {
            User user = userRepository.findByEmail(userEmail);
            return ticketRepository.findAllByCollectionCompanyId(user.getCompanyId(), PAGE);
        }
        return ticketRepository.findAll(PAGE).getContent();
    }

}
