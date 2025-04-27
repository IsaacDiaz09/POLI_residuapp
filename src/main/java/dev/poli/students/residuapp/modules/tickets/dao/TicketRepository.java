package dev.poli.students.residuapp.modules.tickets.dao;

import dev.poli.students.residuapp.modules.tickets.entity.Ticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, String> {

    List<Ticket> findAllByCollectionCompanyId(String collectionCompanyId, Pageable pageable);

    List<Ticket> findAllByCollectionCompanyIdOrCollectionCompanyIdIsNull(String collectionCompanyId, Pageable pageable);

    List<Ticket> findAllByRequestorUserId(String requestorUserId, Pageable pageable);
}
