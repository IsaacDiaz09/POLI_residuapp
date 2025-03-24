package dev.poli.students.residuapp.modules.user;

import dev.poli.students.residuapp.modules.user.dao.UserRoleRepository;
import dev.poli.students.residuapp.modules.user.entity.User;
import dev.poli.students.residuapp.modules.user.entity.UserRole;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class CheckUserRoles {

    private final UserRoleRepository repository;

    @PostConstruct
    public void setupRoles() {
        if (!repository.existsByName(User.Role.UNDEFINED.name())) {
            repository.save(new UserRole(User.Role.UNDEFINED));
        }
        if (!repository.existsByName(User.Role.SYSTEM_ADMIN.name())) {
            repository.save(new UserRole(User.Role.SYSTEM_ADMIN));
        }
        if (!repository.existsByName(User.Role.COMPANY_ADMIN.name())) {
            repository.save(new UserRole(User.Role.COMPANY_ADMIN));
        }
        log.info("system roles OK");
    }
}
