package dev.poli.students.residuapp.modules.auth.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FirebaseLoginRequest {
    @JsonProperty("email")
    private String email;

    @JsonProperty("email")
    private String password;

    @JsonProperty("returnSecureToken")
    private boolean returnSecureToken;

    @JsonProperty("tenantId")
    private String tenantId;
}
