package dev.poli.students.residuapp.modules.user.dao;

import dev.poli.students.residuapp.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Buscar por email (para login)
    Optional<User> findByEmail(String email);

    // Buscar todos los usuarios de un tipo específico (por ejemplo: 'RECOLECTOR' o 'EMPRESA')
    List<User> findByRole(String role);

    // Consulta personalizada: Buscar todos los usuarios ordenados alfabéticamente
    @Query("SELECT u FROM User u ORDER BY u.name ASC")
    List<User> findAllOrderedByName();

    // Consulta personalizada: Buscar usuario por nombre exacto (case sensitive)
    Optional<User> findByName(String name);

    // Consulta personalizada con LIKE (similar a búsqueda)
    @Query("SELECT u FROM User u WHERE u.name LIKE %:keyword%")
    List<User> searchByName(String keyword);
}
