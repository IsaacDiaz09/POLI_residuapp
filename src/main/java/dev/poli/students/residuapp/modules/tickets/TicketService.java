package dev.poli.students.residuapp.modules.tickets;

import dev.poli.students.residuapp.modules.tickets.dao.TicketRepository;
import dev.poli.students.residuapp.modules.tickets.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository repository;

    public List<Ticket> findTickets(String companyId) {
        return this.repository.findAllByCollectionCompanyId(companyId);
    }

    public String generate_report () throws IOException {
        String[] Encabezados = new String[]{
                "Id_Report ",
                "Id_Company ",
                "Id_User ",
                "Status ",
                "Application_Date ",
                "Garbage_type. "
        };
        StringBuilder reporte = new StringBuilder(String.join(",", Encabezados));
        reporte.append("--------------------------------------------------------------------------------------------------------------"+"\n");
        reporte.append("\n");

        List<Ticket> tick = findTickets("ff2a2db3-464b-42d0-8c8f-6b1fb10281db");
        for (Ticket ticket : tick) {
            String fila = ticket.getId() + ", " + ticket.getCollectionCompanyId()+", "+ ticket.getRequestorUserId() + ", " +
                    ticket.getStatus() + ", " + ticket.getCreatedAt() + ", " + ticket.getGarbageType();
            reporte.append(fila).append("\n");
        }
        Files.writeString(Paths.get("", "reporte.txt"), reporte);
        return reporte.toString();
    }
}
