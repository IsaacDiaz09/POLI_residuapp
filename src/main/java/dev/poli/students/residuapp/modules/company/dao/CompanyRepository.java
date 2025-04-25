package dev.poli.students.residuapp.modules.company.dao;

import dev.poli.students.residuapp.modules.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByIdentification(String identification);

    List<Company> findByNameContainingIgnoreCase(String name);

    List<Company> findByStatus(String status);
}
