package org.example.clinicapp.repository;

import org.example.clinicapp.dto.enums.RoleUser;
import org.example.clinicapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);
    boolean existsByEmail(String username);
    Optional<User> findById(Long id);
    List<User> findByRoleUser(RoleUser roleUser);
}
