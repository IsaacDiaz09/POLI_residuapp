package dev.poli.students.residuapp.modules.auth;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.multitenancy.TenantAwareFirebaseAuth;
import dev.poli.students.residuapp.modules.auth.entity.FirebaseLoginRequest;
import dev.poli.students.residuapp.modules.auth.entity.FirebaseLoginResponse;
import dev.poli.students.residuapp.modules.auth.forms.CreateUserForm;
import dev.poli.students.residuapp.modules.auth.forms.LoginForm;
import dev.poli.students.residuapp.modules.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
class FirebaseAuthClient {
    private final String webApiKey;
    private final TenantAwareFirebaseAuth firebaseAuth;
    private final RestTemplate restTemplate;

    @Value("${firebase.auth.tenant-id}")
    private String tenantId;

    public FirebaseAuthClient(@Value("${firebase.credentials.web-apikey.location}") Resource apiKeyFile,
                              RestTemplate restTemplate, TenantAwareFirebaseAuth firebaseAuth) throws IOException {
        this.restTemplate = restTemplate;
        this.webApiKey = apiKeyFile.getContentAsString(StandardCharsets.UTF_8);
        this.firebaseAuth = firebaseAuth;
    }

    public FirebaseLoginResponse doLogin(LoginForm loginForm) {
        String baseURL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key={apiKey}";
        Map<String, String> params = Map.of("apiKey", webApiKey);
        FirebaseLoginRequest loginRequest = FirebaseLoginRequest.builder()
                .email(loginForm.username())
                .password(loginForm.password())
                .returnSecureToken(true)
                .tenantId(tenantId)
                .build();
        return restTemplate.postForObject(baseURL, loginRequest, FirebaseLoginResponse.class, params);
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
