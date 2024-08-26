package org.example.springskeleton.bug;

import core.SpringUnitBaseTest;
import org.example.springskeleton.auth.user.AppUser;
import org.example.springskeleton.developer.Developer;
import org.example.springskeleton.developer.DeveloperRepository;
import org.example.springskeleton.jira.JiraService;
import org.example.springskeleton.project.DeveloperProjectService;
import org.example.springskeleton.project.Project;
import org.example.springskeleton.project.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeveloperBugServiceTest extends SpringUnitBaseTest {
    @InjectMocks
    private DeveloperBugService developerBugService;

    @Mock
    private BugRepository bugRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private BugMapper bugMapper;

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private BugStatusRepository bugStatusRepository;

    @Mock
    private DeveloperProjectService developerProjectService;

    @Mock
    private JiraService jiraService;

    @Test
    void getBugs() {
        Long userId = 1L;
        Long projectId = 1L;
        Pageable pageable = PageRequest.of(0, 5);
        Developer developer = Developer.builder().id(1L).build();
        Bug bug1 = Bug.builder().id(1L).name("Bug 1").build();
        Bug bug2 = Bug.builder().id(2L).name("Bug 2").build();
        Page<Bug> bugPage = new PageImpl<>(List.of(bug1, bug2));
        BugDto bugDto1 = BugDto.builder().id(1L).name("Bug 1").build();
        BugDto bugDto2 = BugDto.builder().id(2L).name("Bug 2").build();
        Page<BugDto> bugDtoPage = new PageImpl<>(List.of(bugDto1, bugDto2));

        when(developerRepository.findByAppUser_Id(userId)).thenReturn(Optional.of(developer));
        when(bugRepository.findByDeveloper_IdAndProject_Id(developer.getId(), projectId, pageable)).thenReturn(bugPage);
        when(bugMapper.toDto(bug1)).thenReturn(bugDto1);
        when(bugMapper.toDto(bug2)).thenReturn(bugDto2);

        Page<BugDto> result = developerBugService.getBugs(userId, projectId, pageable);

        assertEquals(bugDtoPage.getContent(), result.getContent());
        verify(developerRepository, times(1)).findByAppUser_Id(userId);
        verify(bugRepository, times(1)).findByDeveloper_IdAndProject_Id(developer.getId(), projectId, pageable);
    }

    @Test
    void createBug() {
        Long projectId = 1L;
        CreateBugDto createBugDto = CreateBugDto.builder().name("New Bug").description("Bug Description").developer(1L).build();
        Project project = Project.builder().id(projectId).name("Project 1").build();
        Developer developer = Developer.builder().id(1L).appUser(
                AppUser.builder().build()
        ).build();
        Bug bug = Bug.builder().name("New Bug").description("Bug Description").developer(developer).project(project).build();
        BugDto bugDto = BugDto.builder().id(1L).name("New Bug").description("Bug Description").build();

        project.setBugs(new ArrayList<>());
        when(developerProjectService.getProjectById(projectId)).thenReturn(project);
        when(developerRepository.findByAppUser_Id(createBugDto.getDeveloper())).thenReturn(Optional.of(developer));
        when(bugRepository.save(any(Bug.class))).thenReturn(bug);
        when(bugMapper.toDto(bug)).thenReturn(bugDto);

        Optional<BugDto> result = developerBugService.createBug(createBugDto, projectId);

        assertTrue(result.isPresent());
        assertEquals(bugDto, result.get());
        verify(developerProjectService, times(1)).getProjectById(projectId);
        verify(developerRepository, times(1)).findByAppUser_Id(createBugDto.getDeveloper());
        verify(bugRepository, times(1)).save(any(Bug.class));
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void updateBug() {
        Long bugId = 1L;
        CreateBugDto createBugDto = CreateBugDto.builder().name("Updated Bug").description("Updated Description").developer(1L).status("OPEN").build();
        Bug bug = Bug.builder().id(bugId).name("Updated Bug").description("Updated Description").developer(
                Developer.builder()
                        .build()
        ).build();
        BugStatus bugStatus = BugStatus.builder().status(EStatus.OPEN).build();
        BugDto bugDto = BugDto.builder().id(bugId).name("Updated Bug").description("Updated Description").build();

        when(bugRepository.existsById(bugId)).thenReturn(true);
        when(bugRepository.findById(bugId)).thenReturn(Optional.of(bug));
        when(bugStatusRepository.findByStatus(EStatus.OPEN)).thenReturn(Optional.of(bugStatus));
        when(developerRepository.findById(createBugDto.getDeveloper())).thenReturn(Optional.of(bug.getDeveloper()));
        when(bugRepository.save(bug)).thenReturn(bug);
        when(bugMapper.toDto(bug)).thenReturn(bugDto);

        Optional<BugDto> result = developerBugService.updateBug(createBugDto, bugId);

        assertTrue(result.isPresent());
        assertEquals(bugDto, result.get());
        verify(bugRepository, times(1)).existsById(bugId);
        verify(bugRepository, times(1)).findById(bugId);
        verify(bugStatusRepository, times(1)).findByStatus(EStatus.OPEN);
        verify(developerRepository, times(1)).findById(createBugDto.getDeveloper());
        verify(bugRepository, times(1)).save(bug);
    }

    @Test
    void deleteBug() {
        Long bugId = 1L;

        doNothing().when(bugRepository).deleteById(bugId);

        developerBugService.deleteBug(bugId);

        verify(bugRepository, times(1)).deleteById(bugId);
    }

    @Test
    void getBugById() {
        Long bugId = 1L;
        Bug bug = Bug.builder().id(bugId).name("Bug 1").build();
        BugDto bugDto = BugDto.builder().id(bugId).name("Bug 1").build();

        when(bugRepository.existsById(bugId)).thenReturn(true);
        when(bugRepository.findById(bugId)).thenReturn(Optional.of(bug));
        when(bugMapper.toDto(bug)).thenReturn(bugDto);

        Optional<BugDto> result = developerBugService.getBugById(bugId);

        assertTrue(result.isPresent());
        assertEquals(bugDto, result.get());
        verify(bugRepository, times(1)).existsById(bugId);
        verify(bugRepository, times(1)).findById(bugId);
    }
}