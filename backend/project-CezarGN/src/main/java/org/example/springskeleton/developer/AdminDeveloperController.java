package org.example.springskeleton.developer;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.springskeleton.globals.UrlMapping.*;

@RequiredArgsConstructor
@RequestMapping(ADMIN_DEVELOPER_PATH)
@RestController
public class AdminDeveloperController {
    private final AdminDeveloperService adminDeveloperService;
    @GetMapping(GET_DEVELOPERS)
    public ResponseEntity<Page<DeveloperDto>> getDevelopers(@RequestParam String username,
                                                            @RequestParam String seniority,
                                                            @PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok(adminDeveloperService.getDevelopers(username, seniority, pageable));
    }
    @DeleteMapping(DELETE_DEVELOPER)
    public void deleteDeveloper(@PathVariable Long id){
        adminDeveloperService.deleteDeveloper(id);
    }
    @PutMapping(UPDATE_DEVELOPER)
    public ResponseEntity<DeveloperDto> updateDeveloper(@PathVariable Long id, @RequestBody DeveloperDto developerDto) {
        return adminDeveloperService.updateDeveloper(developerDto,id).map(dto -> ResponseEntity.ok().body(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(GET_IDLE_DEVELOPERS)
    public ResponseEntity<Page<DeveloperDto>> getIdleDevelopers(@PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok(adminDeveloperService.getIdleDevelopers(pageable));
    }

    @GetMapping(GET_DEVELOPERS_ON_PROJECT_AND_IDLE)
    public ResponseEntity<Page<DeveloperDto>> getDevelopersOnProjectAndIdle(@PathVariable Long id,@PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok(adminDeveloperService.getDevelopersOnProjectAndIdle(pageable, id));
    }
}
