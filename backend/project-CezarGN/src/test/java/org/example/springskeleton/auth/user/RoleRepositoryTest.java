package org.example.springskeleton.auth.user;

import core.SpringIntegrationBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.mongodb.assertions.Assertions.assertFalse;


class RoleRepositoryTest extends SpringIntegrationBaseTest {
    @Autowired private RoleRepository roleRepository;

    @Test
    void findByName() {
        String roleName = "ADMIN";

        Optional<Role> optionalRole = roleRepository.findByName(ERole.valueOf(roleName));
        assertFalse(optionalRole.isPresent());

    }
}