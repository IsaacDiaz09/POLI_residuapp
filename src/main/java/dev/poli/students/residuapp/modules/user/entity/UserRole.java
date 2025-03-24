package dev.poli.students.residuapp.modules.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    @Id
    private Long id;

    private String name;

    public UserRole(User.Role role) {
        this.id = System.nanoTime();
        this.name = role.name();
    }
}
