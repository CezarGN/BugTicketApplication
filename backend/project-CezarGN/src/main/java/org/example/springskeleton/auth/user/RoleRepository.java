package org.example.springskeleton.auth.user;

import org.example.springskeleton.auth.user.ERole;
import org.example.springskeleton.auth.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);
}
