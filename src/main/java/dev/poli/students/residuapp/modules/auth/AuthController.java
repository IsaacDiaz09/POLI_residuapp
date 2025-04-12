package dev.poli.students.residuapp.modules.auth;

import dev.poli.students.residuapp.modules.auth.forms.LoginForm;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("auth")
public class AuthController {

    @GetMapping("login")
    public ModelAndView showLoginView(@RequestParam(value = "error", required = false) String error, ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String handleLogin(@Valid LoginForm loginForm) {
        log.info("{}", loginForm);
        return "login";
    }

}
