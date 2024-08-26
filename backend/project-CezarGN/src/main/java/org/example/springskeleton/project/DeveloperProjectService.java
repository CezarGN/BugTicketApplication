package org.example.springskeleton.project;

import lombok.RequiredArgsConstructor;
import org.example.springskeleton.developer.DeveloperDto;
import org.example.springskeleton.developer.DeveloperMapper;
import org.example.springskeleton.developer.DeveloperRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DeveloperProjectService {

    private final DeveloperMapper developerMapper;
    private final DeveloperRepository developerRepository;
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;

    public List<DeveloperDto> getDevelopersOnProject(Long projectId) {
        return developerMapper.toDtos(developerRepository.getDeveloperByProjectId(projectId));
    }

    public ProjectDto getDeveloperProject(Long appUserId) {
        return projectMapper.toDto(developerRepository.findByAppUser_Id(appUserId).get().getProject());
    }
    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Project not found"));
    }
}
