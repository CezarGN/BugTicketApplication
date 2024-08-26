package org.example.springskeleton.auth;

import core.SpringControllerBaseTest;
import org.example.springskeleton.auth.user.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.example.springskeleton.globals.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends SpringControllerBaseTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @Mock
    private JwtUtilsService jwtUtilsService;

    @BeforeEach
    public void setUp() {
        super.setUp();
        authController = new AuthController(authService, jwtUtilsService);
        mvc = buildForController(authController);
    }

    @Test
    void register() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("newuser", "password", "DEVELOPER", "JUNIOR");

        when(authService.existsByUsername(signUpRequest.getUsername()))
                .thenReturn(false);

        doNothing().when(authService).register(signUpRequest);

        MessageResponse response = new MessageResponse("User registered successfully!");

        ResultActions res = performPostWithRequestBody(AUTH_PATH + SIGN_UP, signUpRequest);

        verify(authService, times(1)).register(signUpRequest);

        res.andExpect(status().isOk()).andExpect(contentToBe(response));
    }

    @Test
    void registerUsernameTaken() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("newuser", "password", "DEVELOPER", "JUNIOR");

        when(authService.existsByUsername(signUpRequest.getUsername()))
                .thenReturn(true);

        MessageResponse response = new MessageResponse("Error: Username is already taken!");

        ResultActions res = performPostWithRequestBody(AUTH_PATH + SIGN_UP, signUpRequest);

        verify(authService, times(0)).register(signUpRequest);

        res.andExpect(status().isBadRequest()).andExpect(contentToBe(response));
    }

    @Test
    void login() {
        LoginRequest loginRequest = LoginRequest.builder().username("testuser").password("testpassword").build();
        Authentication authentication = mock(Authentication.class);
        when(authService.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        String jwtToken = "testjwt";
        when(jwtUtilsService.generateJwtToken(authentication)).thenReturn(jwtToken);
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("DEVELOPER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "testuser","testpassword",authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        ResponseEntity<JwtResponse> responseEntity = authController.login(loginRequest);

        verify(authService, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));

        verify(jwtUtilsService, times(1)).generateJwtToken(authentication);

        assertEquals(200, responseEntity.getStatusCodeValue());

        JwtResponse jwtResponse = responseEntity.getBody();
        assertEquals(jwtToken, jwtResponse.getToken());
        assertEquals(userDetails.getId(), jwtResponse.getId());
        assertEquals(userDetails.getUsername(), jwtResponse.getUsername());
    }
}
