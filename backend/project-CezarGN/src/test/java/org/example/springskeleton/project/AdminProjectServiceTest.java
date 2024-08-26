package org.example.springskeleton.project;

import core.SpringUnitBaseTest;
import org.example.springskeleton.auth.user.AppUserMapper;
import org.example.springskeleton.developer.DeveloperMapper;
import org.example.springskeleton.developer.DeveloperRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AdminProjectServiceTest extends SpringUnitBaseTest {

    @InjectMocks
    private AdminProjectService adminService;

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private DeveloperMapper developerMapper;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private AppUserMapper appUserMapper;


    @Test
    void createProject() {
        ProjectDto projectDto = new ProjectDto();
        Project project = new Project();
        when(projectMapper.toEntity(projectDto)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.toDto(project)).thenReturn(projectDto);

        Optional<ProjectDto> result = adminService.createProject(projectDto);

        assertFalse(result.isPresent());
    }

    @Test
    void createProject_Failure() {
        ProjectDto projectDto = new ProjectDto();
        when(projectMapper.toEntity(projectDto)).thenReturn(new Project());
        when(projectRepository.save(any())).thenThrow(new RuntimeException());

        Optional<ProjectDto> result = adminService.createProject(projectDto);

        assertFalse(result.isPresent());
    }

    @Test
    void getProjects() {
        Project project = new Project();
        List<Project> projects = Collections.singletonList(project);
        Page<Project> projectPage = new PageImpl<>(projects);
        when(projectRepository.findProjectByNameContaining(anyString(), any(Pageable.class))).thenReturn(projectPage);

        ProjectDto projectDto = new ProjectDto();
        List<ProjectDto> projectDtoList = Collections.singletonList(projectDto);
        Page<ProjectDto> projectDtoPage = new PageImpl<>(projectDtoList);
        when(projectMapper.toDto(project)).thenReturn(projectDto);

        Page<ProjectDto> result = adminService.getProjects("kansdkasjn", PageRequest.of(0, 5));

        assertEquals(1, result.getContent().size());
        assertEquals(projectDto, result.getContent().get(0));
    }

    @Test
    void getProjects_EmptyList() {
        Page<Project> emptyProjectPage = new PageImpl<>(Collections.emptyList());
        when(projectRepository.findProjectByNameContaining(anyString(), any(Pageable.class))).thenReturn(emptyProjectPage);

        Page<ProjectDto> projectDtos = adminService.getProjects("kansdkasjn", PageRequest.of(0, 5));

        assertEquals(0, projectDtos.getContent().size());
    }

    @Test
    void deleteProjectById() {
        Long projectId = 1L;
        when(projectRepository.existsById(projectId)).thenReturn(true);

        adminService.deleteProjectById(projectId);

        verify(projectRepository, times(1)).existsById(projectId);
        verify(projectRepository, times(1)).deleteById(projectId);
    }

    @Test
    void updateProject() {
//        Long projectId = 1L;
//        ProjectDto projectDto = new ProjectDto();
//        Project currentProject = new Project();
//        Project updatedProject = new Project();
//        ProjectDto updatedProjectDto = new ProjectDto();
//        Set<Long> currentDeveloperIds = Set.of(1L, 2L, 3L);
//
//        when(projectRepository.existsById(projectId)).thenReturn(true);
//        when(projectRepository.findById(projectId)).thenReturn(Optional.of(currentProject));
//        when(projectMapper.toDto(updatedProject)).thenReturn(updatedProjectDto);
//
//        Optional<ProjectDto> result = adminService.updateProject(projectDto, projectId);
//
//        assertTrue(result.isPresent());
//        assertEquals(updatedProjectDto, result.get());
//
//        verify(projectRepository, times(1)).existsById(projectId);
//        verify(projectRepository, times(1)).findById(projectId);
//        verify(projectRepository, times(1)).save(updatedProject);
//        verify(projectMapper, times(1)).toDto(updatedProject);
    }
}