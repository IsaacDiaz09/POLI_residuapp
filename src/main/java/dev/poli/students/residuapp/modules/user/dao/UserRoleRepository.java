package dev.poli.students.residuapp.modules.user.dao;

import dev.poli.students.residuapp.modules.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    boolean existsByName(String name);
}
