package org.example.springskeleton.project;

import lombok.RequiredArgsConstructor;
import org.example.springskeleton.project.ProjectDto;
import org.example.springskeleton.project.AdminProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.springskeleton.globals.UrlMapping.*;

@RestController
@RequestMapping(ADMIN_PROJECT_PATH)
@RequiredArgsConstructor
public class AdminProjectController {

    private final AdminProjectService adminProjectService;

    @PostMapping(CREATE_PROJECT)
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {
        return adminProjectService.createProject(projectDto).map(dto -> ResponseEntity.ok().body(dto))
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping(GET_PROJECTS)
    public ResponseEntity<Page<ProjectDto>> getProjects(@RequestParam String name,
                                                        @PageableDefault(size = 3) Pageable pageable) {
        return ResponseEntity.ok(adminProjectService.getProjects(name, pageable));
    }

    @DeleteMapping(DELETE_PROJECT)
    public void deleteProject(@PathVariable Long id) {
        adminProjectService.deleteProjectById(id);
    }


    @PutMapping(UPDATE_PROJECT)
    public ResponseEntity<ProjectDto> updateProject(@PathVariable Long id, @RequestBody ProjectDto projectDto) {
        return adminProjectService.updateProject(projectDto, id).map(dto -> ResponseEntity.ok().body(dto))
                .orElse(ResponseEntity.notFound().build());
    }

}
