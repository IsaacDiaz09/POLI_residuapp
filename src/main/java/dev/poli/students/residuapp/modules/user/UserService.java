package dev.poli.students.residuapp.modules.user;

import dev.poli.students.residuapp.modules.user.dao.UserRepository;
import dev.poli.students.residuapp.modules.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User createUser(String displayName, String companyId) {
        User user = User.builder()
                .status(User.Status.ENABLED)
                .companyId(companyId)
                .name(displayName)
                .build();
        return repository.save(user);
    }

    public byte[] generateReport(List<User> users) {
        String[] headers = new String[]{
                "Id",
                "Nombre",
                "Email",
                "Empresa",
                "Estado"
        };
        StringBuilder sb = new StringBuilder(String.join(",", headers));

        sb.append("\n");

        for (var user : users) {
            sb.append(user.getId())
                    .append(",");

            sb.append(user.getName())
                    .append(",");

            sb.append(user.getEmail())
                    .append(",");

            sb.append(user.getCompanyId() == null ? "" : user.getCompanyId())
                    .append(",");

            sb.append(user.getStatus().name())
                    .append(",");

            sb.append("\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }
}
