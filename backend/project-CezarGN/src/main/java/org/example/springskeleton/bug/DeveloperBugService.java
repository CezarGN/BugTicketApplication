package org.example.springskeleton.bug;

import lombok.RequiredArgsConstructor;
import org.example.springskeleton.developer.Developer;
import org.example.springskeleton.jira.JiraIssueRequestDto;
import org.example.springskeleton.jira.JiraService;
import org.example.springskeleton.project.Project;
import org.example.springskeleton.project.DeveloperProjectService;
import org.example.springskeleton.developer.DeveloperRepository;
import org.example.springskeleton.project.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeveloperBugService {
    private final BugRepository bugRepository;
    private final ProjectRepository projectRepository;
    private final BugMapper bugMapper;
    private final DeveloperRepository developerRepository;
    private final BugStatusRepository bugStatusRepository;
    private final DeveloperProjectService developerProjectService;
    private final JiraService jiraService;

    public Page<BugDto> getBugs(Long userId, Long projectId, Pageable pageable){
            if(userId.intValue() == 0) {
                return bugRepository.findByProject_Id(projectId, pageable).map(bugMapper::toDto);
            } else{
                Long developerId = developerRepository.findByAppUser_Id(userId).get().getId();
                return bugRepository.findByDeveloper_IdAndProject_Id(developerId, projectId, pageable).map(bugMapper::toDto);
            }
    }

    public Optional<BugDto> createBug(CreateBugDto createBugDto, Long projectId){
        try {
            Project project = developerProjectService.getProjectById(projectId);

            Developer developer = getDeveloperFromBugDto(createBugDto);

            Bug bug = bugBuilder(createBugDto, project, developer);
            createJiraIssue(createBugDto, project, developer.getAppUser().getUsername());

            bug = bugRepository.save(bug);

            project.getBugs().add(bug);
            projectRepository.save(project);
            return Optional.of(bugMapper.toDto(bug));
        }catch(Exception e){
            return Optional.empty();
        }
    }

    private void createJiraIssue(CreateBugDto createBugDto, Project project, String username) {
        jiraService.createIssue(createJiraIssueRequestDto(createBugDto, project, username));
    }

    private static JiraIssueRequestDto createJiraIssueRequestDto(CreateBugDto createBugDto, Project project, String asignee) {
        return JiraIssueRequestDto.builder()
                .projectId(project.getId())
                .projectKey(project.getName())
                .description(createBugDto.getName())
                .summary(createBugDto.getDescription())
                .asignee(asignee)
                .build();
    }

    private Bug bugBuilder(CreateBugDto createBugDto, Project project, Developer developer) {
        return Bug.builder()
                .name(createBugDto.getName())
                .description(createBugDto.getDescription())
                .status(
                        BugStatus.builder()
                                .id(1L)
                                .status(EStatus.OPEN)
                                .build()
                )
                .developer(developer)
                .project(project)
                .build();
    }

    private Developer getDeveloperFromBugDto(CreateBugDto createBugDto) {
        return developerRepository.findByAppUser_Id(createBugDto.getDeveloper())
                .orElseThrow(() -> new NoSuchElementException("Developer not found"));
    }

    public Optional<BugDto> updateBug(CreateBugDto createBugDto, Long id){
        if(bugRepository.existsById(id)){
            Bug bug = getAndUpdateBugEntity(createBugDto, id);
            return Optional.of(bugMapper.toDto(bug));
        }else{
            return Optional.empty();
        }
    }

    private Bug getAndUpdateBugEntity(CreateBugDto createBugDto, Long id) {
        Bug bug = bugRepository.findById(id).get();
        setBugEntityAttributes(createBugDto, bug);
        bugRepository.save(bug);
        return bug;
    }

    private void setBugEntityAttributes(CreateBugDto createBugDto, Bug bug) {
        bug.setDescription(createBugDto.getDescription());
        bug.setName(createBugDto.getName());
        bug.setProject(bug.getProject());
        setDeveloperForBug(createBugDto, bug);
        setStatusForBug(createBugDto, bug);
    }

    private void setStatusForBug(CreateBugDto createBugDto, Bug bug) {
        BugStatus bugStatus = bugStatusRepository.findByStatus(EStatus.valueOf(createBugDto.getStatus())).get();
        bug.setStatus(bugStatus);
    }

    private void setDeveloperForBug(CreateBugDto createBugDto, Bug bug) {
        Developer developerForBug = developerRepository.findById(createBugDto.getDeveloper()).get();
        bug.setDeveloper(developerForBug);
    }

    public void deleteBug(Long id){
        bugRepository.deleteById(id);
    }

    public Optional<BugDto> getBugById(Long id){
        if(bugRepository.existsById(id)) {
         return Optional.of(bugMapper.toDto(bugRepository.findById(id).get()));
        }else{
            return Optional.empty();
        }
    }

}
