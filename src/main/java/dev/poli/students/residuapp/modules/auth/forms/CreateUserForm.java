package dev.poli.students.residuapp.modules.auth.forms;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserForm {

    @NotBlank
    private String displayName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @UUID
    @Nullable
    private java.util.UUID companyId;
}
