package dev.poli.students.residuapp.modules.user.dao;

import dev.poli.students.residuapp.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
