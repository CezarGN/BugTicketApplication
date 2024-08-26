package org.example.springskeleton.auth.user;

import core.SpringIntegrationBaseTest;
import org.example.springskeleton.auth.user.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class AppUserRepositoryTest extends SpringIntegrationBaseTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    void findByUsername() {
        String username = "testUser";
        String password = "password";
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(password);
        appUserRepository.save(appUser);

        Optional<AppUser> result = appUserRepository.findByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
    }

    @Test
    void findByUsername_NotExists() {
        String username = "nonExistingUser";

        Optional<AppUser> result = appUserRepository.findByUsername(username);

        assertFalse(result.isPresent());
    }

    @Test
    void existsByUsername() {
        String username = "existingUser";
        String password = "password";
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(password);
        appUserRepository.save(appUser);

        boolean exists = appUserRepository.existsByUsername(username);

        assertTrue(exists);
    }

    @Test
    void existsByUsername_NotExists() {
        String username = "nonExistingUser";

        boolean exists = appUserRepository.existsByUsername(username);

        assertFalse(exists);
    }
}