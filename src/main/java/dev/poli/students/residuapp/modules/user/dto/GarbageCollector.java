package dev.poli.students.residuapp.modules.user.dto;

import dev.poli.students.residuapp.modules.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GarbageCollector {
    private String userId;
    private String personName;
    private String companyName;
    private String email;
    private String status;

    public GarbageCollector(String userid, String personName, String companyName, String email, User.Status status) {
        this.userId = userid;
        this.personName = personName;
        this.companyName = companyName;
        this.email = email;
        this.status = status.name();
    }
}
