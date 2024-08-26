package org.example.springskeleton.jira;

import core.SpringUnitBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.springskeleton.globals.JiraUrls.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class JiraServiceTest extends SpringUnitBaseTest {

    @InjectMocks
    private JiraService jiraService;

    @Mock
    private RestTemplate restTemplate;

    @Value("${jira.token}")
    private String jiraToken;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(jiraService, "jiraToken", "mockJiraToken");
    }

    @Test
    void createProject() {
//        JiraRequestProjectDto jiraRequestProjectDto = JiraRequestProjectDto.builder()
//                .id(1L)
//                .name("TestProject")
//                .description("Test Description")
//                .projectTemplateKey("templateKey")
//                .projectTypeKey("typeKey")
//                .build();
//
//        String expectedResponse = "Project Created";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
//        headers.setCacheControl(CacheControl.noCache());
//        jiraAuthentication(headers);
//
//        Map<String, String> body = new HashMap<>();
//        body.put("key", "TESTP1");
//        body.put("description", "Test Description");
//        body.put("projectTemplateKey", "templateKey");
//        body.put("name", "TestProject");
//        body.put("projectTypeKey", "typeKey");
//        body.put("leadAccountId", JIRA_ACCOUNT_ID);
//        body.put("assigneeType", "PROJECT_LEAD");
//
//        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
//
//        when(restTemplate.exchange(eq(JIRA_CREATE_PROJECT_URL), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
//                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));
//
//        String response = jiraService.createProject(jiraRequestProjectDto);
//
//        assertEquals(expectedResponse, response);
//        verify(restTemplate, times(1)).exchange(eq(JIRA_CREATE_PROJECT_URL), eq(HttpMethod.POST), eq(entity), eq(String.class));
}

    @Test
    void createIssue() {
//        JiraIssueRequestDto jiraIssueRequestDto = JiraIssueRequestDto.builder()
//                .projectId(1L)
//                .projectKey("TEST")
//                .summary("Test Summary")
//                .description("Test Description")
//                .asignee("testAssignee")
//                .build();
//
//        String expectedResponse = "Issue Created";
//        when(restTemplate.exchange(eq(JIRA_CREATE_ISSUE_URL), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
//                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));
//
//        String response = jiraService.createIssue(jiraIssueRequestDto);
//
//        assertEquals(expectedResponse, response);
//        verify(restTemplate, times(1)).exchange(eq(JIRA_CREATE_ISSUE_URL), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void jiraAuthentication() {
//        HttpHeaders headers = new HttpHeaders();
//        jiraAuthentication(headers);
//
//        String auth = JIRA_USERNAME + ":mockJiraToken";
//        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
//        String authHeader = "Basic " + new String(encodedAuth);
//
//        assertEquals(authHeader, headers.getFirst("Authorization"));
    }

    @Test
    void projectNameToProjectKey() {
//        String projectKey = projectNameToProjectKey("TestProject", 1L);
//        assertEquals("TESTP1", projectKey);
    }

    @Test
    void deleteProject(){

    }

    private void jiraAuthentication(HttpHeaders headers) {
        String auth = JIRA_USERNAME + ":mockJiraToken";
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