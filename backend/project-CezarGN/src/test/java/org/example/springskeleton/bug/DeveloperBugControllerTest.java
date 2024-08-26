package org.example.springskeleton.bug;

import core.SpringControllerBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.springskeleton.globals.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeveloperBugControllerTest extends SpringControllerBaseTest {

    @InjectMocks
    private DeveloperBugController developerBugController;

    @Mock
    private DeveloperBugService developerBugService;

    @BeforeEach
    public void setUp() {
        super.setUp();
        developerBugController = new DeveloperBugController(developerBugService);
        mvc = buildForController(developerBugController);
    }

    @Test
    void getBugs() throws Exception {
//        Long id = 1L;
//        Long projectId = 1L;
//        Pageable pageable = PageRequest.of(0, 5);
//        BugDto bug1 = BugDto.builder().id(1L).name("Bug 1").build();
//        BugDto bug2 = BugDto.builder().id(2L).name("Bug 2").build();
//        Page<BugDto> bugPage = new PageImpl<>(List.of(bug1, bug2));
//
//        when(developerBugService.getBugs(id, projectId, pageable)).thenReturn(bugPage);
//
//        List<Pair<String, String>> params = new ArrayList<>(List.of(
//                Pair.of("page", "0"),
//                Pair.of("size", "5")
//        ));
//        ResultActions res = performGetWithParamsAndPathVariables(DEVELOPER_BUG_PATH + GET_BUGS, params, id, projectId);
//
//        verify(developerBugService, times(1)).getBugs(id, projectId, pageable);
//
//        res.andExpect(status().isOk()).andExpect(contentToBe(bugPage));
    }

    @Test
    void createBug() throws Exception {
        Long id = 1L;
        CreateBugDto createBugDto = CreateBugDto.builder().name("New Bug").build();
        BugDto result = BugDto.builder().id(1L).name("New Bug").build();

        when(developerBugService.createBug(createBugDto, id)).thenReturn(Optional.of(result));

        ResultActions res = performPostWithRequestBodyAndPathVariable(DEVELOPER_BUG_PATH + CREATE_BUG, createBugDto, id);

        verify(developerBugService, times(1)).createBug(createBugDto, id);

        res.andExpect(status().isOk()).andExpect(contentToBe(result));
    }

    @Test
    void updateBug() throws Exception {
        Long id = 1L;
        CreateBugDto updateRequestDto = CreateBugDto.builder().name("Updated Bug").build();
        BugDto result = BugDto.builder().id(id).name("Updated Bug").build();

        when(developerBugService.updateBug(updateRequestDto, id)).thenReturn(Optional.of(result));

        ResultActions res = performPutWithPathVariableAndRequestBody(DEVELOPER_BUG_PATH + UPDATE_BUG, id, updateRequestDto);

        verify(developerBugService, times(1)).updateBug(updateRequestDto, id);

        res.andExpect(status().isOk()).andExpect(contentToBe(result));
    }

    @Test
    void deleteBug() throws Exception {
        Long id = 1L;

        doNothing().when(developerBugService).deleteBug(id);

        ResultActions res = performDeleteWithPathVariables(DEVELOPER_BUG_PATH + DELETE_BUG, id);

        verify(developerBugService, times(1)).deleteBug(id);

        res.andExpect(status().isOk());
    }

    @Test
    void getBugById() throws Exception {
        Long id = 1L;
        BugDto result = BugDto.builder().id(id).name("Bug 1").build();

        when(developerBugService.getBugById(id)).thenReturn(Optional.of(result));

        ResultActions res = performGet(DEVELOPER_BUG_PATH + GET_BUG_BY_ID, id);

        verify(developerBugService, times(1)).getBugById(id);

        res.andExpect(status().isOk()).andExpect(contentToBe(result));
    }
}
