package org.example.springskeleton.project;

import lombok.RequiredArgsConstructor;
import org.example.springskeleton.developer.Developer;
import org.example.springskeleton.developer.DeveloperMapper;
import org.example.springskeleton.developer.DeveloperRepository;
import org.example.springskeleton.jira.JiraRequestProjectDto;
import org.example.springskeleton.jira.JiraService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProjectService {
    private final ProjectRepository projectRepository;
    private final DeveloperRepository developerRepository;
    private final ProjectMapper projectMapper;
    private final DeveloperMapper developerMapper;
    private final JiraService jiraService;
    public Optional<ProjectDto> createProject(ProjectDto projectDto) {
        try {
            Project createdProject = projectRepository.save(projectMapper.toEntity(projectDto));
            setProjectDevelopers(createdProject);
            saveProjectInJira(projectDto, createdProject);
            return Optional.of(projectMapper.toDto(createdProject));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Page<ProjectDto> getProjects(String name, Pageable pageable) {
        return projectRepository.findProjectByNameContaining(name, pageable).map(projectMapper::toDto);
    }

    public void deleteProjectById(Long id) {
        if (projectRepository.existsById(id)) {
            Project project = projectRepository.findById(id).get();
            jiraService.deleteProject(project.getName(), project.getId());
            setIdleDevelopers(id);
            projectRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("The element you are trying to delete does not exist");
        }
    }

    private void setIdleDevelopers(Long id) {
        List<Developer> developers = developerRepository.getDeveloperByProjectId(id);
        for (Developer d : developers) {
            d.setProject(null);
            developerRepository.save(d);
        }
    }

    public Optional<ProjectDto> updateProject(ProjectDto projectDto, Long id) {
        if (projectRepository.existsById(id)) {
            Project currentProject = projectRepository.findById(id).orElseThrow();
            Set<Long> currentDeveloperIds = getCurrentDevelopersOnProject(currentProject);

            Project project = getAndUpdateProjectEntity(projectDto, currentProject);
            updateDevelopersOnProject(project, currentDeveloperIds);

            projectRepository.save(project);
            return Optional.of(projectMapper.toDto(project));
        } else {
            return Optional.empty();
        }
    }

    private static Set<Long> getCurrentDevelopersOnProject(Project oldProject) {
        List<Developer> oldDevelopers = oldProject.getDevelopers();
        return oldDevelopers.stream().map(Developer::getId).collect(Collectors.toSet());
    }

    private void updateDevelopersOnProject(Project updatedProject, Set<Long> oldDeveloperIds) {
        List<Developer> updatedDevelopers = updatedProject.getDevelopers();
        Set<Long> updatedDeveloperIds = updatedDevelopers.stream().map(Developer::getId).collect(Collectors.toSet());

        oldDeveloperIds.stream()
                .filter(id -> !updatedDeveloperIds.contains(id))
                .forEach(id -> {
                    Developer dev = developerRepository.findById(id).orElseThrow();
                    dev.setProject(null);
                    developerRepository.save(dev);
                });

        updatedDevelopers.forEach(dev -> {
            if (!oldDeveloperIds.contains(dev.getId())) {
                Developer developer = developerRepository.findById(dev.getId()).orElseThrow();
                developer.setProject(updatedProject);
                developerRepository.save(developer);
            }
        });
    }

    private Project getAndUpdateProjectEntity(ProjectDto projectDto, Project project) {
        setProjectEntityAttributes(projectDto, project);
        return project;
    }

    private void setProjectEntityAttributes(ProjectDto projectDto, Project project) {
        project.setDescription(projectDto.getDescription());
        project.setName(projectDto.getName());
        List<Developer> updatedDevelopers = projectDto.getDevelopers().stream()
                .map(developerMapper::toEntity)
                .collect(Collectors.toList());
        project.setDevelopers(updatedDevelopers);
    }

    private void saveProjectInJira(ProjectDto projectDto, Project createdProject) {
        JiraRequestProjectDto jiraRequestProjectDto = buildJiraProjectRequestDto(projectDto, createdProject);
        jiraService.createProject(jiraRequestProjectDto);
    }

    private JiraRequestProjectDto buildJiraProjectRequestDto(ProjectDto projectDto, Project createdProject) {
        return JiraRequestProjectDto.builder()
                .id(createdProject.getId())
                .name(projectDto.getName())
                .key(projectDto.getName())
                .projectTypeKey("software")
                .projectTemplateKey(projectDto.getProjectTemplateKey())
                .description(projectDto.getDescription())
                .build();
    }


    private void setProjectDevelopers(Project createdProject) {
        for (Developer d : createdProject.getDevelopers()) {
            Developer developer = developerRepository.findById(d.getId()).get();
            developer.setProject(createdProject);
            developerRepository.save(developer);
        }
    }



}