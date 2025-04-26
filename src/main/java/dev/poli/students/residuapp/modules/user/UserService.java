package dev.poli.students.residuapp.modules.user;

import dev.poli.students.residuapp.modules.user.dao.UserRepository;
import dev.poli.students.residuapp.modules.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
