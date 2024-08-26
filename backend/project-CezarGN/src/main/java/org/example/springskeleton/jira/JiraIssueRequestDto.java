package org.example.springskeleton.jira;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
public class JiraIssueRequestDto {
    private Long projectId;
    private String projectKey;
    private String description;
    private String summary;
    private String asignee;
}
/*
"fields": {
       "project":
       {
          "key": "TEST"
       },
       "summary": "REST ye merry gentlemen.",
       "description": "Creating of an issue using project keys and issue type names using the REST API",
       "issuetype": {
          "name": "Bug"
       }
 */