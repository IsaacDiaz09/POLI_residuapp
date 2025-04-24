package dev.poli.students.residuapp.modules.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

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
        modelAndView.setViewName("tickets");
        return modelAndView;
    }

    @GetMapping("users")
    public ModelAndView users(ModelAndView modelAndView) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("user", authentication);
        modelAndView.setViewName("users");
        return modelAndView;
    }

}
