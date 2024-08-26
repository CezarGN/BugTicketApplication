package org.example.springskeleton.auth;

import lombok.RequiredArgsConstructor;
import org.example.springskeleton.developer.Developer;
import org.example.springskeleton.developer.seniority.ESeniority;
import org.example.springskeleton.developer.seniority.Seniority;
import org.example.springskeleton.auth.user.AppUser;
import org.example.springskeleton.auth.user.ERole;
import org.example.springskeleton.auth.user.AppUserRepository;
import org.example.springskeleton.developer.DeveloperRepository;
import org.example.springskeleton.auth.user.RoleRepository;
import org.example.springskeleton.developer.SeniorityRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AppUserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final RoleRepository roleRepository;
  private final DeveloperRepository developerRepository;
  private final SeniorityRepository seniorityRepository;
  private final PasswordEncoder encoder;

  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public void register(SignUpRequest signUpRequest) {
    AppUser user = AppUser.builder()
            .username(signUpRequest.getUsername())
            .password(encoder.encode(signUpRequest.getPassword()))
            .build();

    String role = signUpRequest.getRole();
    user.setRole(roleRepository.findByName(ERole.valueOf(role)).get());

    userRepository.save(user);

    if(role.equals(String.valueOf(ERole.DEVELOPER))) {
      Seniority seniority = seniorityRepository.findBySeniority(ESeniority.valueOf(signUpRequest.getSeniority())).get();
      Developer developer = Developer.builder()
              .appUser(user)
              .seniority(seniority)
              .build();
      developerRepository.save(developer);
    }

  }

  public Authentication authenticate(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
    return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
  }
}
