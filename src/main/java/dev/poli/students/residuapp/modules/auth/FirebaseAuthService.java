package dev.poli.students.residuapp.modules.auth;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import dev.poli.students.residuapp.modules.auth.entity.FirebaseLoginResponse;
import dev.poli.students.residuapp.modules.auth.forms.CreateUserForm;
import dev.poli.students.residuapp.modules.auth.forms.LoginForm;
import dev.poli.students.residuapp.modules.user.UserService;
import dev.poli.students.residuapp.modules.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseAuthService {

    private final FirebaseAuthClient firebaseClient;
    private final UserService userService;

    public User createUser(CreateUserForm userForm) throws FirebaseAuthException {
        User.Role userRole = userForm.getCompanyId() == null ? User.Role.UNDEFINED : User.Role.COMPANY_ADMIN;
        UserRecord firebaseUser = firebaseClient.createUser(userForm, userRole);
        log.info("user created in Google Firebase with ID: {}", firebaseUser.getUid());

        User user = userService.createUser(userForm.getDisplayName(), userForm.getCompanyId());
        log.info("user created with ID: {} Email: {}", user.getId(), userForm.getEmail());
        return user;
    }

    public FirebaseToken verifyIdToken(String idToken) throws FirebaseAuthException {
        return firebaseClient.verifyIdToken(idToken);
    }

    public FirebaseLoginResponse doLogin(LoginForm loginForm) throws IOException, InterruptedException {
        return firebaseClient.doLogin(loginForm);
    }

    public UserRecord getUser(String email) throws FirebaseAuthException {
        return firebaseClient.getUser(email);
    }

}
