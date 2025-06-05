package dev.poli.students.residuapp.modules;

import dev.poli.students.residuapp.modules.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class AuthUtils {
    private AuthUtils() {
    }

    public static String getAuthenticatedUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public static User.Role getAuthenticatedUserRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) auth.getAuthorities();
        String role = authorities.get(0).getAuthority().replace("ROLE_", "");
        return User.Role.valueOf(role);
    }
}
