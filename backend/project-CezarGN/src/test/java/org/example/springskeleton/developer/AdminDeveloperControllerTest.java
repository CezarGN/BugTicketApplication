package org.example.springskeleton.developer;

import core.SpringControllerBaseTest;
import org.example.springskeleton.auth.user.AppUserDto;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminDeveloperControllerTest extends SpringControllerBaseTest {

    @InjectMocks
    private AdminDeveloperController adminDeveloperController;

    @Mock
    private AdminDeveloperService adminDeveloperService;

    @BeforeEach
    public void setUp() {
        super.setUp();
        adminDeveloperController = new AdminDeveloperController(adminDeveloperService);
        mvc = buildForController(adminDeveloperController);
    }

    @Test
    void getDevelopers() throws Exception {
//        String username = "testuser";
//        String seniority = "senior";
//        Pageable pageable = PageRequest.of(0, 5);
//        DeveloperDto developerDto1 = DeveloperDto.builder().id(1L).appUser(
//                AppUserDto.builder()
//                        .username("user1")
//                        .build()
//        ).build();
//        DeveloperDto developerDto2 = DeveloperDto.builder().id(2L).appUser(
//                AppUserDto.builder()
//                        .username("user1")
//                        .build()
//        ).build();
//        Page<DeveloperDto> developerPage = new PageImpl<>(List.of(developerDto1, developerDto2));
//
//        when(adminDeveloperService.getDevelopers(username, seniority, pageable)).thenReturn(developerPage);
//
//        List<Pair<String, String>> params = new ArrayList<>(List.of(
//                Pair.of("username", username),
//                Pair.of("seniority", seniority),
//                Pair.of("page", "0"),
//                Pair.of("size", "5")
//        ));
//        ResultActions res = performGetWithParams(ADMIN_DEVELOPER_PATH + GET_DEVELOPERS, params);
//
//        verify(adminDeveloperService, times(1)).getDevelopers(username, seniority, pageable);
//
//        res.andExpect(status().isOk()).andExpect(contentToBe(developerPage));
    }

    @Test
    void deleteDeveloper() throws Exception {
//        Long id = 1L;
//
//        doNothing().when(adminDeveloperService).deleteDeveloper(id);
//
//        ResultActions res = performDeleteWithPathVariables(ADMIN_DEVELOPER_PATH + DELETE_DEVELOPER, id);
//
//        verify(adminDeveloperService, times(1)).deleteDeveloper(id);
//
//        res.andExpect(status().isOk());
    }

    @Test
    void updateDeveloper() throws Exception {
//        Long id = 1L;
//        DeveloperDto developerDto = DeveloperDto.builder().id(id).appUser(
//                AppUserDto.builder()
//                        .username("user1")
//                        .build()
//        ).build();
//        DeveloperDto result = DeveloperDto.builder().id(id).appUser(
//                AppUserDto.builder()
//                        .username("user2")
//                        .build()
//        ).build();
//
//        when(adminDeveloperService.updateDeveloper(developerDto, id)).thenReturn(Optional.of(result));
//
//        ResultActions res = performPutWithPathVariableAndRequestBody(ADMIN_DEVELOPER_PATH + UPDATE_DEVELOPER, developerDto, id);
//
//        verify(adminDeveloperService, times(1)).updateDeveloper(developerDto, id);
//
//        res.andExpect(status().isOk()).andExpect(contentToBe(result));
    }

    @Test
    void getIdleDevelopers() throws Exception {
//        Pageable pageable = PageRequest.of(0, 5);
//        DeveloperDto developerDto1 = DeveloperDto.builder().id(1L).appUser(
//                AppUserDto.builder()
//                        .username("user1")
//                        .build()
//        ).build();
//        DeveloperDto developerDto2 = DeveloperDto.builder().id(2L).appUser(
//                AppUserDto.builder()
//                        .username("user2")
//                        .build()
//        ).build();
//        Page<DeveloperDto> developerPage = new PageImpl<>(List.of(developerDto1, developerDto2));
//
//        when(adminDeveloperService.getIdleDevelopers(pageable)).thenReturn(developerPage);
//
//        ResultActions res = performGetWithParamsAndPathVariables(ADMIN_DEVELOPER_PATH + GET_IDLE_DEVELOPERS, List.of(
//                Pair.of("page", "0"),
//                Pair.of("size", "5")
//        ));
//
//        verify(adminDeveloperService, times(1)).getIdleDevelopers(pageable);
//
//        res.andExpect(status().isOk()).andExpect(contentToBe(developerPage));
    }

    @Test
    void getDevelopersOnProjectAndIdle() throws Exception {
//        Long projectId = 1L;
//        Pageable pageable = PageRequest.of(0, 5);
//        DeveloperDto developerDto1 = DeveloperDto.builder().id(1L).appUser(
//                AppUserDto.builder()
//                        .username("user1")
//                        .build()
//        ).build();
//        DeveloperDto developerDto2 = DeveloperDto.builder().id(2L).appUser(
//                AppUserDto.builder()
//                        .username("user2")
//                        .build()
//        ).build();
//        Page<DeveloperDto> developerPage = new PageImpl<>(List.of(developerDto1, developerDto2));
//
//        when(adminDeveloperService.getDevelopersOnProjectAndIdle(pageable, projectId)).thenReturn(developerPage);
//
//        ResultActions res = performGetWithParamsAndPathVariables(ADMIN_DEVELOPER_PATH + GET_DEVELOPERS_ON_PROJECT_AND_IDLE, List.of(
//                Pair.of("page", "0"),
//                Pair.of("size", "5")
//        ), projectId);
//
//        verify(adminDeveloperService, times(1)).getDevelopersOnProjectAndIdle(pageable, projectId);
//
//        res.andExpect(status().isOk()).andExpect(contentToBe(developerPage));
    }
}