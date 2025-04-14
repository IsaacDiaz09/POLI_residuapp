package dev.poli.students.residuapp.modules.auth;

import dev.poli.students.residuapp.modules.auth.entity.FirebaseLoginResponse;
import dev.poli.students.residuapp.modules.auth.forms.LoginForm;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final FirebaseAuthService authService;

    @GetMapping("login")
    public ModelAndView showLoginView(@RequestParam(value = "error", required = false) String error, ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void handleLogin(@Valid LoginForm loginForm, HttpServletResponse response) throws IOException {
        try {
            FirebaseLoginResponse loginData = authService.doLogin(loginForm);
            ResponseCookie resCookie = ResponseCookie.from("AUTH", loginData.getIdToken())
                    .httpOnly(true)
                    .sameSite("None")
                    .secure(true)
                    .path("/")
                    .maxAge(loginData.getExpiresInSeconds())
                    .build();
            response.addHeader("Set-Cookie", resCookie.toString());
            response.sendRedirect("/poliresiduapp/home");
        } catch (InterruptedException | IOException e) {
            response.sendRedirect("/poliresiduapp/auth/login?error=" + e.getMessage());
        }
    }
}
