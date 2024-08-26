package org.example.springskeleton.project;

import core.SpringControllerBaseTest;
import org.example.springskeleton.developer.DeveloperDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;

import static org.example.springskeleton.globals.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeveloperProjectControllerTest extends SpringControllerBaseTest {

    @InjectMocks
    private DeveloperProjectController developerProjectController;

    @Mock
    private DeveloperProjectService developerProjectService;

    @BeforeEach
    public void setUp() {
        developerProjectController = new DeveloperProjectController(developerProjectService);
        mvc = buildForController(developerProjectController);
    }

    @Test
    void getDevelopersOnProject() throws Exception {
        DeveloperDto developerDto = new DeveloperDto();
        List<DeveloperDto> developers = Collections.singletonList(developerDto);

        when(developerProjectService.getDevelopersOnProject(anyLong())).thenReturn(developers);

        ResultActions res = performGetWithPathVariables(DEVELOPER_PROJECT_PATH + GET_DEVELOPERS_ON_PROJECT, 1L);

        verify(developerProjectService, times(1)).getDevelopersOnProject(anyLong());
        res.andExpect(status().isOk()).andExpect(contentToBe(developers));
    }

    @Test
    void getDevelopersOnProject_EmptyList() throws Exception {
        when(developerProjectService.getDevelopersOnProject(anyLong())).thenReturn(Collections.emptyList());

        ResultActions res = performGetWithPathVariables(DEVELOPER_PROJECT_PATH + GET_DEVELOPERS_ON_PROJECT, 1L);

        verify(developerProjectService, times(1)).getDevelopersOnProject(anyLong());
        res.andExpect(status().isOk()).andExpect(contentToBe(Collections.emptyList()));
    }

    @Test
    void getDeveloperProject() throws Exception {
        ProjectDto projectDto = new ProjectDto();

        when(developerProjectService.getDeveloperProject(anyLong())).thenReturn(projectDto);

        ResultActions res = performGetWithPathVariables(DEVELOPER_PROJECT_PATH + GET_DEVELOPER_PROJECT, 1L);

        verify(developerProjectService, times(1)).getDeveloperProject(anyLong());
        res.andExpect(status().isOk()).andExpect(contentToBe(projectDto));
    }

}
