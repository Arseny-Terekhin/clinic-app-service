package org.example.clinicapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.clinicapp.dto.enums.RoleUser;

import javax.lang.model.element.Name;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@ToString
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private RoleUser roleUser;

    @Column(name = "medBook")
    private String medBook;
}
