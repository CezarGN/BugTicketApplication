package org.example.springskeleton.auth;

import core.SpringUnitBaseTest;
import org.example.springskeleton.auth.user.*;
import org.example.springskeleton.developer.Developer;
import org.example.springskeleton.developer.DeveloperRepository;
import org.example.springskeleton.developer.SeniorityRepository;
import org.example.springskeleton.developer.seniority.ESeniority;
import org.example.springskeleton.developer.seniority.Seniority;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AuthServiceTest extends SpringUnitBaseTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private SeniorityRepository seniorityRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void existsByUsername() {
        String username = "testuser";
        when(userRepository.existsByUsername(username)).thenReturn(true);

        boolean exists = authService.existsByUsername(username);

        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsername(username);
    }

    @Test
    void register() {
        SignUpRequest signUpRequest = new SignUpRequest("newuser", "password", "DEVELOPER", "JUNIOR");

        AppUser user = AppUser.builder()
                .username(signUpRequest.getUsername())
                .password("encodedPassword")
                .role(
                        Role.builder()
                                .name(ERole.DEVELOPER)
                                .build()
                )
                .build();

        when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName(ERole.DEVELOPER)).thenReturn(Optional.of(Role.builder().name(ERole.DEVELOPER).build()));
        when(seniorityRepository.findBySeniority(ESeniority.JUNIOR)).thenReturn(Optional.of(Seniority.builder().seniority(ESeniority.JUNIOR).build()));

        authService.register(signUpRequest);

        verify(userRepository, times(1)).save(user);
        verify(developerRepository, times(1)).save(any(Developer.class));
    }

    @Test
    void authenticate() {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken("user", "password");
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(authToken)).thenReturn(authentication);

        org.springframework.security.core.Authentication result = authService.authenticate(authToken);

        assertEquals(authentication, result);
        verify(authenticationManager, times(1)).authenticate(authToken);
    }
}