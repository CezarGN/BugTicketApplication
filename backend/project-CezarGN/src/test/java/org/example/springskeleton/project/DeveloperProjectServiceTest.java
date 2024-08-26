package org.example.springskeleton.project;

import core.SpringUnitBaseTest;
import org.example.springskeleton.developer.Developer;
import org.example.springskeleton.developer.DeveloperDto;
import org.example.springskeleton.developer.DeveloperMapper;
import org.example.springskeleton.developer.DeveloperRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeveloperProjectServiceTest extends SpringUnitBaseTest {
    @Mock
    private DeveloperMapper developerMapper;

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private DeveloperProjectService developerProjectService;

    @BeforeEach
    void setUp() {
        developerProjectService = new DeveloperProjectService(developerMapper, developerRepository, projectMapper, projectRepository);
    }

    @Test
    void getDevelopersOnProject() {
        Long projectId = 1L;
        Developer developer = new Developer();
        List<Developer> developers = Collections.singletonList(developer);
        DeveloperDto developerDto = new DeveloperDto();
        List<DeveloperDto> developerDtos = Collections.singletonList(developerDto);

        when(developerRepository.getDeveloperByProjectId(projectId)).thenReturn(developers);
        when(developerMapper.toDtos(developers)).thenReturn(developerDtos);

        List<DeveloperDto> result = developerProjectService.getDevelopersOnProject(projectId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(developerDto, result.get(0));

        verify(developerRepository, times(1)).getDeveloperByProjectId(projectId);
        verify(developerMapper, times(1)).toDtos(developers);
    }

    @Test
    void getDeveloperProject() {
        Long appUserId = 1L;
        Developer developer = new Developer();
        Project project = new Project();
        developer.setProject(project);
        ProjectDto projectDto = new ProjectDto();

        when(developerRepository.findByAppUser_Id(appUserId)).thenReturn(Optional.of(developer));
        when(projectMapper.toDto(project)).thenReturn(projectDto);

        ProjectDto result = developerProjectService.getDeveloperProject(appUserId);

        assertNotNull(result);
        assertEquals(projectDto, result);

        verify(developerRepository, times(1)).findByAppUser_Id(appUserId);
        verify(projectMapper, times(1)).toDto(project);
    }

    @Test
    void getDeveloperProject_NotFound() {
        Long appUserId = 1L;

        when(developerRepository.findByAppUser_Id(appUserId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> developerProjectService.getDeveloperProject(appUserId));
        verify(developerRepository, times(1)).findByAppUser_Id(appUserId);
        verify(projectMapper, times(0)).toDto(any(Project.class));
    }

    @Test
    void getProjectById() {
        Long projectId = 1L;
        Project project = new Project();

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        Project result = developerProjectService.getProjectById(projectId);

        assertNotNull(result);
        assertEquals(project, result);

        verify(projectRepository, times(1)).findById(projectId);
    }

    @Test
    void getProjectById_NotFound() {
        Long projectId = 1L;

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> developerProjectService.getProjectById(projectId));
        verify(projectRepository, times(1)).findById(projectId);
    }
}