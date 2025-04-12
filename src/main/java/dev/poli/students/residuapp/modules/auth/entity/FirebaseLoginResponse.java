package dev.poli.students.residuapp.modules.auth.entity;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FirebaseLoginResponse {
    @SerializedName("kind")
    private String kind;

    @SerializedName("localId")
    private String localId;

    @SerializedName("email")
    private String email;

    @SerializedName("displayName")
    private String displayName;

    @SerializedName("idToken")
    private String idToken;

    @SerializedName("registered")
    private boolean registered;

    @SerializedName("refreshToken")
    private String refreshToken;

    @SerializedName("expiresIn")
    private int expiresInSeconds;
}
