package dev.poli.students.residuapp.modules.auth.filters;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import dev.poli.students.residuapp.modules.auth.FirebaseAuthService;
import dev.poli.students.residuapp.modules.user.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionTokenAuthenticationFilter extends OncePerRequestFilter {

    private final String cookieName = "AUTH";
    private final FirebaseAuthService authService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException, ServletException {
        Cookie authenticationCookie = getSessionCookie(request);
        if (authenticationCookie != null) {
            String idToken = authenticationCookie.getValue();
            try {
                FirebaseToken userInformation = authService.verifyIdToken(idToken);
                User.Role userRole = User.Role.valueOf((String) userInformation.getClaims().getOrDefault("ROLES",
                        User.Role.UNDEFINED.name()));
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name()));
                var authentication = new UsernamePasswordAuthenticationToken(userInformation.getEmail(), null,
                        authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } catch (FirebaseAuthException e) {
                handleAuthenticationError(response, e.getMessage());
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private void handleAuthenticationError(HttpServletResponse response, String errorMessage) throws IOException {
        log.error(errorMessage);
        response.sendRedirect("/poliresiduapp/auth/login?error=" + errorMessage);
    }

    private Cookie getSessionCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies() == null ? new Cookie[]{} : request.getCookies();
        return Stream.of(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst()
                .orElse(null);
    }
}
