package dev.poli.students.residuapp;

import dev.poli.students.residuapp.modules.tickets.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;

import java.util.Arrays;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication(exclude = {
        RestTemplateAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class,
})
public class Application implements CommandLineRunner {

    @Autowired
    private TicketService ticketService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Application running with arguments: {}", Arrays.toString(args));
        log.info("Server running on TimeZone: '{}'", TimeZone.getDefault().getID());
        log.info("{}", ticketService.findTickets("ff2a2db3-464b-42d0-8c8f-6b1fb10281db"));
    }
}
