package org.example.springskeleton.project;

import core.SpringIntegrationBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

class ProjectRepositoryTest extends SpringIntegrationBaseTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void findProjectByNameContaining() {
//        String name = "test project";
//        Project project = Project.builder().name(name).build();
//        projectRepository.save(project);
//
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Project> foundProjects = projectRepository.findProjectByNameContaining("test", pageable);
//
//        assertTrue(foundProjects.hasContent());
//        assertEquals(1, foundProjects.getTotalElements());
//        assertEquals(name, foundProjects.getContent().get(0).getName());
    }
}