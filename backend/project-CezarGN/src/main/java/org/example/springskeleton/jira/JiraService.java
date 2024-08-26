package org.example.springskeleton.jira;

import org.example.springskeleton.config.JiraProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.springskeleton.globals.JiraUrls.*;

@Service
public class JiraService {

    private JiraProperties jiraProperties;

    public String createProject(JiraRequestProjectDto jiraRequestProjectDto) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = getHttpHeaders();

        jiraAuthentication(headers);
        Map<String, String> body = createJiraProjectJSONBody(jiraRequestProjectDto);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(JIRA_CREATE_PROJECT_URL, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }

    private Map<String, String> createJiraProjectJSONBody(JiraRequestProjectDto jiraRequestProjectDto) {
        String projectKey = projectNameToProjectKey(jiraRequestProjectDto.getName(), jiraRequestProjectDto.getId());
        Map<String, String> body = new HashMap<>();
        body.put("key", projectKey);
        body.put("description", jiraRequestProjectDto.getDescription());
        body.put("projectTemplateKey", jiraRequestProjectDto.getProjectTemplateKey());
        body.put("name", jiraRequestProjectDto.getName());
        body.put("projectTypeKey", jiraRequestProjectDto.getProjectTypeKey());
        body.put("leadAccountId", JIRA_ACCOUNT_ID);
        body.put("assigneeType", "PROJECT_LEAD");
        return body;
    }

    public String createIssue(JiraIssueRequestDto jiraIssueRequestDto) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = getHttpHeaders();

        jiraAuthentication(headers);

        Map<String, Object> body = new HashMap<>();
        Map<String, String> project = new HashMap<>();
        project.put("key", projectNameToProjectKey(jiraIssueRequestDto.getProjectKey(), jiraIssueRequestDto.getProjectId()));

        Map<String, String> issuetype = new HashMap<>();
        issuetype.put("name", "Bug");

        Map<String, Object> fields = new HashMap<>();
        fields.put("project", project);
        fields.put("summary", jiraIssueRequestDto.getDescription());
        fields.put("description", jiraIssueRequestDto.getSummary());
        fields.put("issuetype", issuetype);
        fields.put("assignee", jiraIssueRequestDto.getAsignee());

        body.put("fields", fields);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(JIRA_CREATE_ISSUE_URL, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }

    public void deleteProject(String projectName, Long id){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHttpHeaders();
        jiraAuthentication(headers);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        restTemplate.exchange(JIRA_CREATE_PROJECT_URL + "/" +projectNameToProjectKey(projectName, id), HttpMethod.DELETE, entity, String.class);
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setCacheControl(CacheControl.noCache());
        return headers;
    }

    private void jiraAuthentication(HttpHeaders headers) {
        String auth = JIRA_USERNAME + ":" + jiraProperties.getToken();
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
    }

    private String projectNameToProjectKey(String projectName, Long id) {
        String upperCaseProjectName = projectName.toUpperCase();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < 5; i++){
            stringBuilder.append(upperCaseProjectName.charAt(i));
        }
        stringBuilder.append(id);
        return stringBuilder.toString();
    }
}
