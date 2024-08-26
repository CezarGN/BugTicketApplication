package org.example.springskeleton.bug;

import core.SpringIntegrationBaseTest;
import org.example.springskeleton.auth.user.AppUser;
import org.example.springskeleton.auth.user.AppUserRepository;
import org.example.springskeleton.developer.Developer;
import org.example.springskeleton.developer.DeveloperRepository;
import org.example.springskeleton.project.Project;
import org.example.springskeleton.project.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BugRepositoryTest extends SpringIntegrationBaseTest {

    @Autowired
    private BugRepository bugRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    void findByDeveloper_IdAndProject_Id() {
//        Developer developer = Developer.builder()
//                .build();
//        developer = developerRepository.save(developer);
//
//        Project project = Project.builder()
//                .name("name")
//                .description("description")
//                .build();
//        project = projectRepository.save(project);
//
//        Bug bug = Bug.builder()
//                .description("description")
//                .developer(developer)
//                .project(project)
//                .build();
//        bug = bugRepository.save(bug);
//
//        // When
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Bug> foundBugs = bugRepository.findByDeveloper_IdAndProject_Id(developer.getId(), project.getId(), pageable);
//
//        // Then
//        assertTrue(foundBugs.hasContent());
//        assertEquals(1, foundBugs.getTotalElements());
//        assertEquals(developer.getId(), foundBugs.getContent().get(0).getDeveloper().getId());
//        assertEquals(project.getId(), foundBugs.getContent().get(0).getProject().getId());
    }

    @Test
    void findByProject_Id() {
        Project project = Project.builder()
                .name("name")
                .description("description")
                .build();
        projectRepository.save(project);

        Bug bug1 = Bug.builder()
                .name("name")
                .project(project)
                .description("description")
                .build();
        Bug bug2 = Bug.builder()
                .name("name1")
                .project(project)
                .description("description")
                .build();
        bugRepository.save(bug1);
        bugRepository.save(bug2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Bug> foundBugs = bugRepository.findByProject_Id(project.getId(), pageable);

        assertTrue(foundBugs.hasContent());
        assertEquals(2, foundBugs.getTotalElements());
        foundBugs.forEach(bug -> assertEquals(project.getId(), bug.getProject().getId()));
    }
}
