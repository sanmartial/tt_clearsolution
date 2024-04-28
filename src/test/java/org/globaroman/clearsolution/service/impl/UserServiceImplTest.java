package org.globaroman.clearsolution.service.impl;

import org.globaroman.clearsolution.dto.AddressRequestDto;
import org.globaroman.clearsolution.dto.CreateUserRequestDto;
import org.globaroman.clearsolution.dto.SearchUserParameter;
import org.globaroman.clearsolution.dto.UpdatePhoneRequestDto;
import org.globaroman.clearsolution.dto.UserResponseDto;
import org.globaroman.clearsolution.exception.RegistrationException;
import org.globaroman.clearsolution.mapper.AddressMapper;
import org.globaroman.clearsolution.mapper.UserMapper;
import org.globaroman.clearsolution.model.Address;
import org.globaroman.clearsolution.model.User;
import org.globaroman.clearsolution.repository.UserRepository;
import org.globaroman.clearsolution.shareObjects.ObjectsForTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressMapper addressMapper;

    private ObjectsForTest objects;

    @BeforeEach
    void setUp() {
        objects = new ObjectsForTest();
        ReflectionTestUtils.setField(userService, "limitAge", 18L);
    }

    @Test
    @DisplayName("Should create a new user")
    void create_ShouldCreateNewUser_Result_Ok() {
        User user = objects.getNewUser();
        Address address = objects.getAddress();
        CreateUserRequestDto requestDto = objects.getCreateUserRequestDto();

        Mockito.when(userMapper.toModel(requestDto)).thenReturn(user);
        Mockito.when(addressMapper.toModel(requestDto.getAddressRequestDto())).thenReturn(address);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userMapper.toDto(user)).thenReturn(objects.getUserResponseDto());

        UserResponseDto result = userService.create(requestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());

    }

    @Test
    @DisplayName("When age of user less than necessary limit - throw exception")
    void create_ShouldCreateNewUserWithWrongAge_Result_ThrowException() {
        User user = objects.getNewUser();
        user.setBirthDate(LocalDate.parse("2017-01-01"));
        Address address = objects.getAddress();
        CreateUserRequestDto requestDto = objects.getCreateUserRequestDto();
        requestDto.setBirthDate(LocalDate.parse("2017-01-01"));

        Mockito.when(userMapper.toModel(requestDto)).thenReturn(user);
        Mockito.when(addressMapper.toModel(requestDto.getAddressRequestDto())).thenReturn(address);

        RuntimeException exception = Assertions.assertThrows(RegistrationException.class,
                () -> userService.create(requestDto));

        String expected = "The user has not yet reached the age of 18";
        String actual = exception.getMessage();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should update user information")
    void updateUser_ShouldUpdateUser_Result_Ok() {
        User user = objects.getNewUser();
        CreateUserRequestDto requestDto = objects.getCreateUserRequestDto();

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userMapper.toUpdate(requestDto, user)).thenReturn(user);
        Mockito.when(userMapper.toDto(user)).thenReturn(objects.getUserResponseDto());

        UserResponseDto result = userService.updateUser(1L, requestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Should update user address")
    void updateAddressUser_ShouldUpdateUserAddress_Result_Ok() {
        Long userId = 1L;
        AddressRequestDto addressRequestDto = objects.getAddressDto();

        User user = objects.getNewUser();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(addressMapper.toModel(addressRequestDto)).thenReturn(new Address());
        Mockito.when(userMapper.toDto(user)).thenReturn(new UserResponseDto());

        UserResponseDto result = userService.updateAddressUser(userId, addressRequestDto);

        assertNotNull(result);
    }

    @Test
    @DisplayName("Should update user phone number")
    void updatePhoneUser_ShouldUpdateUserPhoneNumber_Result_Ok() {
        Long userId = 1L;

        User user = new User();
        user.setId(userId);
        UpdatePhoneRequestDto requestDto = new UpdatePhoneRequestDto();
        requestDto.setPhone("+380674443322");

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userMapper.toDto(user)).thenReturn(new UserResponseDto());

        UserResponseDto result = userService.updatePhoneUser(userId, requestDto);

        assertNotNull(result);
    }

    @Test
    @DisplayName("Should delete a user")
    void deleteUser_ShouldDeleteUser_Result_Ok() {
        Long userId = 1L;
        userService.deleteUser(userId);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("Should search for birthdate from - to by users")
    void searchUsers_ShouldSearchUsers_Result_Ok() {

        SearchUserParameter searchUserParameter = objects.getSearchUserParameter();

        User user1 = objects.getNewUser();
        user1.setBirthDate(LocalDate.of(1990, 1, 1));
        User user2 = objects.getNewUser();
        user2.setBirthDate(LocalDate.of(1995, 1, 1));
        User user3 = objects.getNewUser();
        user1.setBirthDate(LocalDate.of(1965, 1, 1));

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<UserResponseDto> result = userService.searchUsers(searchUserParameter);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }
}
