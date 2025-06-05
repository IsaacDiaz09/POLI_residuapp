package dev.poli.students.residuapp.modules.tickets;

import dev.poli.students.residuapp.modules.tickets.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    public byte[] generateReport(List<Ticket> tickets) {
        String[] Encabezados = new String[]{
                "Id_Ticket",
                "Id_Company",
                "Localidad",
                "Id_User",
                "Status",
                "Application_Date",
                "Garbage_type"
        };
        StringBuilder reporte = new StringBuilder(String.join(",", Encabezados));
        reporte.append("\n");

        for (Ticket ticket : tickets) {
            String fila = ticket.getId() + "," +
                    ticket.getCollectionCompanyId() + "," +
                    ticket.getRequestedLocation() + "," +
                    ticket.getRequestorUserId() + "," +
                    ticket.getStatus() + "," +
                    ticket.getCreatedAt() + "," +
                    ticket.getGarbageType();
            reporte.append(fila).append("\n");
        }
        return reporte.toString().getBytes(StandardCharsets.UTF_8);
    }
}
