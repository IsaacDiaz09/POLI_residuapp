package dev.poli.students.residuapp.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.multitenancy.TenantAwareFirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

@Configuration
class FirebaseConfig {

    @Bean
    TenantAwareFirebaseAuth firebaseAuth(Environment env) throws IOException {
        var credentialsLocation = env.getRequiredProperty("firebase.credentials.file.location");
        var credentialsFile = ResourceUtils.getFile(credentialsLocation);
        try (var credentials = Files.newInputStream(credentialsFile.toPath())) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(Objects.requireNonNull(credentials)))
                    .build();
            var firebase = FirebaseApp.initializeApp(options);
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(firebase);
            var tenantId = env.getRequiredProperty("firebase.auth.tenant-id");
            return firebaseAuth.getTenantManager().getAuthForTenant(tenantId);
        }
    }


}
