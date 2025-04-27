package dev.poli.students.residuapp.modules.auth;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import dev.poli.students.residuapp.modules.auth.entity.FirebaseLoginResponse;
import dev.poli.students.residuapp.modules.auth.forms.LoginForm;
import dev.poli.students.residuapp.modules.user.entity.User;
import io.netty.util.internal.StringUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final FirebaseAuthService authService;

    @GetMapping("login")
    public ModelAndView showLoginView(@RequestParam(value = "error", required = false) String error,
                                      ModelAndView modelAndView, HttpServletResponse response) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            response.sendRedirect("/poliresiduapp/admin");
            return null;
        }
        modelAndView.setViewName("login");
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void handleLogin(@Valid LoginForm loginForm, HttpServletResponse response) throws IOException {
        try {
            FirebaseLoginResponse loginData = authService.doLogin(loginForm);
            UserRecord user = authService.getUser(loginData.getEmail());
            String role = (String) user.getCustomClaims().getOrDefault("ROLES", User.Role.UNDEFINED.name());
            ResponseCookie resCookie = ResponseCookie.from("AUTH", loginData.getIdToken())
                    .httpOnly(true)
                    .sameSite("Strict")
                    .secure(false)
                    .path("/")
                    .maxAge(loginData.getExpiresInSeconds())
                    .build();
            response.addHeader("Set-Cookie", resCookie.toString());
            response.sendRedirect(buildRedirectPath(role));
        } catch (InterruptedException | IOException | FirebaseAuthException e) {
            response.sendRedirect("/poliresiduapp/auth/login?error=" + e.getMessage());
        }
    }

    @PostMapping("logout")
    public RedirectView logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Use Optional to handle the case where the cookie might not exist.
        Optional<Cookie> authCookie = request.getCookies() != null ? Stream.of(request.getCookies())
                .filter(c -> c.getName().equals("AUTH"))
                .findFirst() : Optional.empty();

        if (authCookie.isPresent()) {
            Cookie auth = authCookie.get();
            auth.setMaxAge(0);
            auth.setPath("/");
            response.addCookie(auth);
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/home");
        redirectView.setContextRelative(true);
        return redirectView;
    }

    private String buildRedirectPath(String userRole) {
        User.Role role = StringUtil.isNullOrEmpty(userRole) ? User.Role.UNDEFINED : User.Role.valueOf(userRole);
        if (role == User.Role.SYSTEM_ADMIN) {
            return "/poliresiduapp/admin";
        }
        return "/poliresiduapp/tickets";
    }

}
