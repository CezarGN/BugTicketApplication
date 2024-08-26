package org.example.springskeleton.developer;

import core.SpringUnitBaseTest;
import org.example.springskeleton.auth.user.AppUser;
import org.example.springskeleton.auth.user.AppUserDto;
import org.example.springskeleton.auth.user.AppUserMapper;
import org.example.springskeleton.developer.seniority.ESeniority;
import org.example.springskeleton.developer.seniority.Seniority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminDeveloperServiceTest extends SpringUnitBaseTest {

    @InjectMocks
    private AdminDeveloperService adminDeveloperService;

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private SeniorityRepository seniorityRepository;

    @Mock
    private DeveloperMapper developerMapper;

    @Mock
    private AppUserMapper appUserMapper;

    @Test
    void getDevelopers() {
        String username = "testuser";
        String seniority = "SENIOR";
        Pageable pageable = PageRequest.of(0, 5);
        Developer developer = Developer.builder().id(1L).build();
        DeveloperDto developerDto = DeveloperDto.builder().id(1L).appUser(
                AppUserDto.builder()
                        .username(username)
                        .build()
                ).build();
        Page<Developer> developerPage = new PageImpl<>(List.of(developer));
        Page<DeveloperDto> developerDtoPage = new PageImpl<>(List.of(developerDto));

        when(developerRepository.findByUsernameContainingAndSeniority(username, ESeniority.SENIOR, pageable)).thenReturn(developerPage);
        when(developerMapper.toDto(developer)).thenReturn(developerDto);

        Page<DeveloperDto> result = adminDeveloperService.getDevelopers(username, seniority, pageable);

        assertEquals(developerDtoPage.getContent(), result.getContent());
        verify(developerRepository, times(1)).findByUsernameContainingAndSeniority(username, ESeniority.SENIOR, pageable);
    }

    @Test
    void updateDeveloper() {
        Long id = 1L;
        DeveloperDto developerDto = DeveloperDto.builder().id(id).appUser(
                AppUserDto.builder()
                        .id(1L)
                        .username("testuser")
                        .build()
        )
                .seniority("SENIOR")
                .build();
        AppUser appUser = AppUser.builder().id(1L).username("updateduser").build();
        Developer developer = Developer.builder().id(id)
                .appUser(appUser).build();
        Seniority seniority = Seniority.builder().id(1L).seniority(ESeniority.SENIOR).build();

        when(developerRepository.existsById(id)).thenReturn(true);
        when(developerRepository.findById(id)).thenReturn(Optional.of(developer));
        when(appUserMapper.toEntity(developerDto.getAppUser())).thenReturn(appUser);
        when(seniorityRepository.findBySeniority(ESeniority.SENIOR)).thenReturn(Optional.of(seniority));
        when(developerMapper.toDto(developer)).thenReturn(developerDto);

        Optional<DeveloperDto> result = adminDeveloperService.updateDeveloper(developerDto, id);

        assertTrue(result.isPresent());
        assertEquals(developerDto, result.get());
        verify(developerRepository, times(1)).existsById(id);
        verify(developerRepository, times(1)).findById(id);
        verify(appUserMapper, times(1)).toEntity(developerDto.getAppUser());
        verify(seniorityRepository, times(1)).findBySeniority(ESeniority.SENIOR);
        verify(developerRepository, times(1)).save(developer);
    }

    @Test
    void deleteDeveloper() {
        Long id = 1L;

        doNothing().when(developerRepository).deleteById(id);

        adminDeveloperService.deleteDeveloper(id);

        verify(developerRepository, times(1)).deleteById(id);
    }

    @Test
    void getIdleDevelopers() {
        Pageable pageable = PageRequest.of(0, 5);
        Developer developer = Developer.builder().id(1L).build();
        DeveloperDto developerDto = DeveloperDto.builder().id(1L).appUser(
                AppUserDto.builder()
                        .username("testuser")
                        .build()
        ).build();
        Page<Developer> developerPage = new PageImpl<>(List.of(developer));
        Page<DeveloperDto> developerDtoPage = new PageImpl<>(List.of(developerDto));

        when(developerRepository.getIdleDevelopers(pageable)).thenReturn(developerPage);
        when(developerMapper.toDto(developer)).thenReturn(developerDto);

        Page<DeveloperDto> result = adminDeveloperService.getIdleDevelopers(pageable);

        assertEquals(developerDtoPage.getContent(), result.getContent());
        verify(developerRepository, times(1)).getIdleDevelopers(pageable);
    }

    @Test
    void getDevelopersOnProjectAndIdle() {
        Long projectId = 1L;
        Pageable pageable = PageRequest.of(0, 5);
        Developer developer = Developer.builder().id(1L).build();
        DeveloperDto developerDto = DeveloperDto.builder().id(1L).appUser(
                AppUserDto.builder()
                        .username("testUser")
                        .build()
        ).build();
        Page<Developer> developerPage = new PageImpl<>(List.of(developer));
        Page<DeveloperDto> developerDtoPage = new PageImpl<>(List.of(developerDto));

        when(developerRepository.getDevelopersOnProjectAndIdle(pageable, projectId)).thenReturn(developerPage);
        when(developerMapper.toDto(developer)).thenReturn(developerDto);

        Page<DeveloperDto> result = adminDeveloperService.getDevelopersOnProjectAndIdle(pageable, projectId);

        assertEquals(developerDtoPage.getContent(), result.getContent());
        verify(developerRepository, times(1)).getDevelopersOnProjectAndIdle(pageable, projectId);
    }
}