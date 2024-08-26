package org.example.springskeleton.project;

import lombok.RequiredArgsConstructor;
import org.example.springskeleton.developer.DeveloperDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.example.springskeleton.globals.UrlMapping.*;

@RequiredArgsConstructor
@RequestMapping(DEVELOPER_PROJECT_PATH)
@RestController
public class DeveloperProjectController {
    private final DeveloperProjectService developerProjectService;
    @GetMapping(GET_DEVELOPERS_ON_PROJECT)
    public ResponseEntity<List<DeveloperDto>> getDevelopersOnProject(@PathVariable Long id){
        return ResponseEntity.ok(developerProjectService.getDevelopersOnProject(id));
    }
    @GetMapping(GET_DEVELOPER_PROJECT)
    public ResponseEntity<ProjectDto> getDeveloperProject(@PathVariable Long id){
        return ResponseEntity.ok(developerProjectService.getDeveloperProject(id));
    }
}
