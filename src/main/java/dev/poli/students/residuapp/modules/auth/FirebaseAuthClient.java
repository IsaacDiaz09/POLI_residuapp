package dev.poli.students.residuapp.modules.auth;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.multitenancy.TenantAwareFirebaseAuth;
import com.google.gson.Gson;
import dev.poli.students.residuapp.modules.auth.domain.InvalidCredentialsException;
import dev.poli.students.residuapp.modules.auth.entity.FirebaseLoginRequest;
import dev.poli.students.residuapp.modules.auth.entity.FirebaseLoginResponse;
import dev.poli.students.residuapp.modules.auth.forms.CreateUserForm;
import dev.poli.students.residuapp.modules.auth.forms.LoginForm;
import dev.poli.students.residuapp.modules.user.entity.User;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
class FirebaseAuthClient {
    private static final Gson GSON = new Gson();

    private final TenantAwareFirebaseAuth firebaseAuth;
    private final String webApiKey;

    @Value("${firebase.auth.tenant-id}")
    private String tenantId;

    public FirebaseAuthClient(@Value("${firebase.credentials.web-apikey.location}") Resource apiKeyFile,
                              TenantAwareFirebaseAuth firebaseAuth) throws IOException {
        this.webApiKey = apiKeyFile.getContentAsString(StandardCharsets.UTF_8);
        this.firebaseAuth = firebaseAuth;
    }

    public UserRecord getUser(String email) throws FirebaseAuthException {
        return firebaseAuth.getUserByEmail(email);
    }

    public FirebaseLoginResponse doLogin(LoginForm loginForm) throws IOException, InterruptedException {
        URI requestURI = URI.create("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + webApiKey);
        FirebaseLoginRequest loginRequest = FirebaseLoginRequest.builder()
                .email(loginForm.username())
                .password(loginForm.password())
                .returnSecureToken(true)
                .tenantId(tenantId)
                .build();

        HttpRequest request = HttpRequest.newBuilder(requestURI)
                .header("Content-Type", ContentType.APPLICATION_JSON.getMimeType())
                .timeout(Duration.of(10, ChronoUnit.SECONDS))
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(loginRequest)))
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (response.statusCode() >= 400 && response.statusCode() <= 599) {
            throw new InvalidCredentialsException("Credenciales invalidas");
        }
        return GSON.fromJson(response.body(), FirebaseLoginResponse.class);
    }

    public UserRecord createUser(CreateUserForm form, User.Role withRole) throws FirebaseAuthException {
        var request = new UserRecord.CreateRequest();
        request.setEmail(form.getEmail());
        request.setDisplayName(form.getDisplayName());
        request.setPassword(form.getPassword());
        request.setEmailVerified(true);
        var user = firebaseAuth.createUser(request);

        var update = user.updateRequest();
        var claims = user.getCustomClaims();
        claims.put("ROLES", withRole.name());
        update.setCustomClaims(claims);
        firebaseAuth.updateUser(update);

        return user;
    }

    public FirebaseToken verifyIdToken(String idToken) throws FirebaseAuthException {
        return firebaseAuth.verifyIdToken(idToken);
    }

}
