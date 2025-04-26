package dev.poli.students.residuapp;

import lombok.extern.slf4j.Slf4j;
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


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Application running with arguments: {}", Arrays.toString(args));
        log.info("Server running on TimeZone: '{}'", TimeZone.getDefault().getID());
    }
}
