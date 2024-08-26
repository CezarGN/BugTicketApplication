package org.example.springskeleton.bug;

import lombok.RequiredArgsConstructor;
import org.example.springskeleton.project.DeveloperProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.springskeleton.globals.UrlMapping.*;

@RestController
@RequestMapping(DEVELOPER_BUG_PATH)
@RequiredArgsConstructor
public class DeveloperBugController {
    private final DeveloperBugService developerBugService;

    @GetMapping(GET_BUGS)
    public ResponseEntity<Page<BugDto>> getBugs(@PathVariable Long id,
                                                @PathVariable Long projectId,
                                                @PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok(developerBugService.getBugs(id, projectId, pageable));
    }

    @PostMapping(CREATE_BUG)
    public ResponseEntity<BugDto> createBug(@PathVariable Long id, @RequestBody CreateBugDto createBugDto){
        return developerBugService.createBug(createBugDto, id).map(dto -> ResponseEntity.ok().body(dto))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping(UPDATE_BUG)
    public ResponseEntity<BugDto> updateBug(@PathVariable Long id, @RequestBody CreateBugDto createBugDto){
        return developerBugService.updateBug(createBugDto, id).map(dto -> ResponseEntity.ok().body(dto))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping(DELETE_BUG)
    public void deleteBug(@PathVariable Long id){
        developerBugService.deleteBug(id);
    }


    @GetMapping(GET_BUG_BY_ID)
    public ResponseEntity<BugDto> getBugById(@PathVariable Long id){
        return developerBugService.getBugById(id).map(dto -> ResponseEntity.ok().body(dto))
                .orElse(ResponseEntity.notFound().build());
    }

}
