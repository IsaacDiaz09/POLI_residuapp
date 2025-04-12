package dev.poli.students.residuapp.modules.auth.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class FirebaseLoginResponse {
    @JsonProperty("kind")
    private String kind;

    @JsonProperty("localId")
    private String localId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("idToken")
    private String idToken;

    @JsonProperty("registered")
    private boolean registered;

    @JsonProperty("refreshToken")
    private String refreshToken;

    @JsonProperty("expiresIn")
    private int expiresInSeconds;
}
