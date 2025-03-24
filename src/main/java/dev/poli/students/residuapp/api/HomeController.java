package dev.poli.students.residuapp.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView, Model model) {
        modelAndView.setViewName("login");
        modelAndView.addObject("username", "jhondoe@yopmail.com");
        return modelAndView;
    }
}
