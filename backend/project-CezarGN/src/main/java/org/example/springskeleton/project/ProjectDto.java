package org.example.springskeleton.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springskeleton.bug.BugDto;
import org.example.springskeleton.developer.DeveloperDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private List<DeveloperDto> developers;
    private List<BugDto> bugs;
    private String projectTemplateKey;
}
