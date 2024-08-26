package org.example.springskeleton.project;

import core.SpringControllerBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.example.springskeleton.globals.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminProjectControllerTest extends SpringControllerBaseTest {

    @InjectMocks
    private AdminProjectController adminController;

    @Mock
    private AdminProjectService adminService;


    @BeforeEach
    public void setUp() {
        super.setUp();
        adminController = new AdminProjectController(adminService);
        mvc = buildForController(adminController);
    }

    @Test
    void createProject() throws Exception {
        ProjectDto projectDto = ProjectDto.builder().name("name").description("description").build();

        when(adminService.createProject(projectDto)).thenReturn(Optional.of(projectDto));

        ResultActions res = performPostWithRequestBody(ADMIN_PROJECT_PATH + CREATE_PROJECT, projectDto);

        verify(adminService, times(1)).createProject(projectDto);

        res.andExpect(status().isOk()).andExpect(contentToBe(projectDto));
    }

    @Test
    void createProject_Failure() throws Exception {
        ProjectDto projectDto = new ProjectDto();

        when(adminService.createProject(projectDto)).thenReturn(Optional.empty());

        ResultActions res = performPostWithRequestBody(ADMIN_PROJECT_PATH + CREATE_PROJECT, projectDto);

        verify(adminService, times(1)).createProject(projectDto);

        res.andExpect(status().isBadRequest());
    }

    @Test
    void updateProject() throws Exception {
        ProjectDto projectDto = new ProjectDto();
        Long id = 1L;

        when(adminService.updateProject(projectDto, id)).thenReturn(Optional.of(projectDto));

        ResultActions res = performPutWithPathVariableAndRequestBody(ADMIN_PROJECT_PATH + UPDATE_PROJECT, id, projectDto);

        verify(adminService, times(1)).updateProject(projectDto, id);

        res.andExpect(status().isOk()).andExpect(contentToBe(projectDto));
    }

    @Test
    void updateProject_Failure() throws Exception {
        ProjectDto projectDto = new ProjectDto();
        Long id = 1L;

        when(adminService.updateProject(projectDto, id)).thenReturn(Optional.empty());

        ResultActions res = performPutWithPathVariableAndRequestBody(ADMIN_PROJECT_PATH + UPDATE_PROJECT, id, projectDto);

        verify(adminService, times(1)).updateProject(projectDto, id);

        res.andExpect(status().isNotFound());
    }


    @Test
    void getProjects() throws Exception {
//        ProjectDto projectDto = new ProjectDto();
//        List<ProjectDto> projectDtoList = Collections.singletonList(projectDto);
//        Page<ProjectDto> projectDtoPage = new PageImpl<>(projectDtoList);
//        when(adminService.getProjects(anyString(), any(Pageable.class))).thenReturn(projectDtoPage);
//
//        ResultActions res = performGetWithParams()
//                .param("name", "kansdkasjn")
//                .param("page", "0")
//                .param("size", "5")
//                .contentType(MediaType.APPLICATION_JSON);
//
//        verify(adminService, times(1)).getProjects(anyString(), any(Pageable.class));
//        res.andExpect(status().isOk())
//                .andExpect((ResultMatcher) content().json(new ObjectMapper().writeValueAsString(projectDtoPage)))
    }


    @Test
    void deleteProject() throws Exception {
        Long projectId = 1L;
        ResultActions res = performDeleteWithPathVariables(ADMIN_PROJECT_PATH + DELETE_PROJECT, projectId);
        verify(adminService, times(1)).deleteProjectById(projectId);
        res.andExpect(status().isOk());
    }

}