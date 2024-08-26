package org.example.springskeleton.config;

import lombok.RequiredArgsConstructor;
import org.example.springskeleton.auth.AuthService;
import org.example.springskeleton.auth.SignUpRequest;
import org.example.springskeleton.auth.user.AppUserRepository;
import org.example.springskeleton.auth.user.ERole;
import org.example.springskeleton.auth.user.Role;
import org.example.springskeleton.auth.user.RoleRepository;
import org.example.springskeleton.bug.BugStatus;
import org.example.springskeleton.bug.BugStatusRepository;
import org.example.springskeleton.bug.EStatus;
import org.example.springskeleton.developer.SeniorityRepository;
import org.example.springskeleton.developer.seniority.ESeniority;
import org.example.springskeleton.developer.seniority.Seniority;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@RequiredArgsConstructor
public class Bootstrap {

  private final RoleRepository roleRepository;
  private final SeniorityRepository seniorityRepository;
  private final BugStatusRepository bugStatusRepository;
  private final AppUserRepository appUserRepository;
  private final AuthService authService;
  @EventListener(ApplicationReadyEvent.class)
  public void bootstrapData() {
    for (ERole value : ERole.values()) {
      if (roleRepository.findByName(value).isEmpty()) {
        roleRepository.save(
            Role.builder().name(value).build()
        );
      }
    }

    for(ESeniority val : ESeniority.values()){
      if(seniorityRepository.findBySeniority(val).isEmpty()){
        seniorityRepository.save(
                Seniority.builder()
                        .seniority(val)
                        .build()
        );
      }
    }

    for(EStatus val : EStatus.values()){
      if(bugStatusRepository.findByStatus(val).isEmpty()){
        bugStatusRepository.save(
                BugStatus.builder()
                        .status(val)
                        .build()
        );
      }
    }
    if(appUserRepository.findByUsername("admin1").isEmpty()){
      authService.register(
              SignUpRequest.builder()
                      .username("admin1")
                      .password("parolaAdmin")
                      .role("ADMIN")
                      .build()
      );
    }

  }
}