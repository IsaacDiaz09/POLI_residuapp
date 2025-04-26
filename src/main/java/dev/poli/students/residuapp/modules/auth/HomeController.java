package dev.poli.students.residuapp.modules.auth;

import dev.poli.students.residuapp.modules.tickets.dao.TicketReportRepository;
import dev.poli.students.residuapp.modules.tickets.dao.TicketRepository;
import dev.poli.students.residuapp.modules.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {

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
        Pageable page = PageRequest.of(0, 9);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("user", authentication);
        modelAndView.addObject("tickets", ticketRepository.findAll(page));
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
        modelAndView.addObject("reports", ticketReportRepository.findAll(
                PageRequest.of(0, 7)).getContent());
        modelAndView.setViewName("report");
        return modelAndView;
    }

}
