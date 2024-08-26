package org.example.springskeleton.jira;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JiraRequestProjectDto {
    private Long id;
    private String key;
    private String name;
    private String projectTypeKey;
    private String projectTemplateKey;
    private String description;
}
