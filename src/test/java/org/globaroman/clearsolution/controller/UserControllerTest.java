package org.globaroman.clearsolution.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.globaroman.clearsolution.dto.AddressRequestDto;
import org.globaroman.clearsolution.dto.CreateUserRequestDto;
import org.globaroman.clearsolution.dto.UpdatePhoneRequestDto;
import org.globaroman.clearsolution.dto.UserResponseDto;
import org.globaroman.clearsolution.service.UserService;
import org.globaroman.clearsolution.shareObjects.ObjectsForTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;
    private ObjectsForTest obj;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        obj = new ObjectsForTest();
    }

    @Test
    @DisplayName("Create a new user --> Result OK")
    void createNewUser_successfulCreate_ShouldReturnResultOk() throws Exception {

        CreateUserRequestDto requestDto = obj.getCreateUserRequestDto();
        UserResponseDto expectedResponseDto = obj.getUserResponseDto();

        Mockito.when(userService.create(Mockito.any(CreateUserRequestDto.class)))
                .thenReturn(expectedResponseDto);

        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/api/v1/users")

                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expectedResponseDto.getId()))
                .andExpect(jsonPath("$.firstName").value(expectedResponseDto.getFirstName()));
    }

    @Test
    @DisplayName("Create a new user with wrong email --> Result Exp")
    void createNewUser_WhenEmailWrong_ShouldThrowsException() throws Exception {

        CreateUserRequestDto requestDto = obj.getCreateUserRequestDto();
        requestDto.setEmail("user#example.com");

        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/api/v1/users")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Create a new user with wrong phone --> Result Exp")
    void createNewUser_WhenPhoneWrong_ShouldThrowsException() throws Exception {

        CreateUserRequestDto requestDto = obj.getCreateUserRequestDto();
        requestDto.setPhone("+3806711122444");

        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/api/v1/users")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Update all fields user --> Result OK")
    void updateUser_WHenAllFieldsRight_ShouldBeResult_Ok() throws Exception {
        Long userId = 1L;
        CreateUserRequestDto requestDto = obj.getCreateUserRequestDto();
        UserResponseDto expectedResponseDto = obj.getUserResponseDto();
        Mockito.when(userService.updateUser(userId, requestDto)).thenReturn(expectedResponseDto);

        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(patch("/api/v1/users/1", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expectedResponseDto.getId()))
                .andExpect(jsonPath("$.firstName").value(expectedResponseDto.getFirstName()));
    }

    @Test
    @DisplayName("Update address of user --> Result OK")
    void updateAddressUser_WHenChangeAddress_ShouldResult_Ok() throws Exception {
        Long userId = 1L;
        CreateUserRequestDto requestDto = obj.getCreateUserRequestDto();
        UserResponseDto expectedResponseDto = obj.getUserResponseDto();
        AddressRequestDto addressRequestDto = obj.getAddressDto();

        Mockito.when(userService.updateAddressUser(userId, addressRequestDto))
                .thenReturn(expectedResponseDto);

        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequest = objectMapper.writeValueAsString(addressRequestDto);

        mockMvc.perform(patch("/api/v1/users/address/1", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expectedResponseDto.getId()))
                .andExpect(jsonPath("$.firstName").value(expectedResponseDto.getFirstName()));
    }

    @Test
    @DisplayName("Update phone of user --> Result OK")
    void updatePhoneUser_WHenChangePhone_ShouldReturnResult_Ok() throws Exception {
        Long userId = 1L;
        CreateUserRequestDto requestDto = obj.getCreateUserRequestDto();
        UserResponseDto expectedResponseDto = obj.getUserResponseDto();
        UpdatePhoneRequestDto updatePhoneRequestDto = obj.getUpdatePhoneDto();
        expectedResponseDto.setPhone(updatePhoneRequestDto.getPhone());

        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequest = objectMapper.writeValueAsString(updatePhoneRequestDto);

        Mockito.when(userService.updatePhoneUser(userId, updatePhoneRequestDto))
                .thenReturn(expectedResponseDto);

        mockMvc.perform(patch("/api/v1/users/phone/1", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expectedResponseDto.getId()))
                .andExpect(jsonPath("$.phone").value(expectedResponseDto.getPhone()));
    }

    @Test
    void deleteUser() throws Exception {
        long userId = 123;

        mockMvc.perform(delete("/api/v1/users/123", userId))
                .andExpect(status().isOk());

        Mockito.verify(userService).deleteUser(userId);
    }

}
