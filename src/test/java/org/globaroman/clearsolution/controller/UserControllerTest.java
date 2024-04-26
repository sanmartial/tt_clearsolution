package org.globaroman.clearsolution.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.globaroman.clearsolution.dto.AddressRequestDto;
import org.globaroman.clearsolution.dto.CreateUserRequestDto;
import org.globaroman.clearsolution.dto.UserResponseDto;
import org.globaroman.clearsolution.model.Address;
import org.globaroman.clearsolution.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void createNewUser() throws Exception {

        CreateUserRequestDto requestDto = getCreateUserRequestDto();
        UserResponseDto expectedResponseDto = getUserResponseDto();

        Mockito.when(userService.create(Mockito.any(CreateUserRequestDto.class)))
                .thenReturn(expectedResponseDto);

        mockMvc.perform(post("/api/v1/users")

                        .content(asJsonString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expectedResponseDto.getId()))
                .andExpect(jsonPath("$.firstName").value(expectedResponseDto.getFirstName()));
    }

    private UserResponseDto getUserResponseDto() {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(1L);
        userResponseDto.setPhone("+380671112233");
        userResponseDto.setEmail("user@example.com");
        userResponseDto.setFirstName("Name");
        userResponseDto.setLastName("Lastname");
        userResponseDto.setBirthDate(LocalDate.parse("1999-01-01"));
        userResponseDto.setAddress(getAddress());

        return userResponseDto;
    }

    private CreateUserRequestDto getCreateUserRequestDto() {
        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        requestDto.setEmail("user@example.com");
        requestDto.setFirstName("Name");
        requestDto.setLastName("Lastname");
        requestDto.setPhone("+380671112233");
        requestDto.setBirthDate("01-01-1999");
        requestDto.setAddressRequestDto(getAddressDto());

        return requestDto;
    }

    private AddressRequestDto getAddressDto() {
        AddressRequestDto addressRequestDto = new AddressRequestDto();
        addressRequestDto.setCity("City");
        addressRequestDto.setStreet("Street");
        addressRequestDto.setBuilding("100");
        addressRequestDto.setApartment("15");
        addressRequestDto.setPostCode("23731");
        return addressRequestDto;
    }

    private Address getAddress() {
        Address addressRequestDto = new Address();
        addressRequestDto.setCity("City");
        addressRequestDto.setStreet("Street");
        addressRequestDto.setBuilding("100");
        addressRequestDto.setApartment("15");
        addressRequestDto.setPostCode("23731");
        return addressRequestDto;
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateUser() {
    }

    @Test
    void updateAddressUser() {
    }

    @Test
    void updatePhoneUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void searchUserByBirthDate() {
    }

    @Test
    void getUserById() {
    }
}
