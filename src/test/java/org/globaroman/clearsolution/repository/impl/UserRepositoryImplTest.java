package org.globaroman.clearsolution.repository.impl;

import org.globaroman.clearsolution.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @AfterEach
    void tearDown() {
        userRepository.findAll().clear();
    }
    @Test
    public void save_ValidUser_ShouldReturnSavedUser() {
        User expected = new User();
        expected.setId(1L);
        expected.setFirstName("name");

        User actual = userRepository.save(expected);

        Assertions.assertEquals(expected, actual);
    }

    @DisplayName("Find all users, should return all valid users")
    @Test
    public void findAll_ValidUsers_ShouldReturnAllValidUsers() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("name");

        List<User> expected = List.of(user);

        userRepository.findAll().add(user);

        List<User> actual = userRepository.findAll();

        Assertions.assertEquals(expected, actual);
    }

    @DisplayName("Find the user by id, should return valid user")
    @Test
    public void findById_ValidId_ShouldReturnValidUser() {
        Long id = 1L;

        User expected = new User();
        expected.setId(id);
        expected.setFirstName("name");

        userRepository.findAll().add(expected);

        Optional<User> actual = userRepository.findById(id);

        Assertions.assertEquals(expected, actual.get());
    }

    @DisplayName("Delete the user by id,should return true")
    @Test
    public void deleteById_ValidId_ShouldReturnTrue() {
        Long id = 1L;

        User user = new User();
        user.setId(id);
        user.setFirstName("name");

        userRepository.findAll().add(user);

        boolean expected = true;
        boolean actual = userRepository.deleteById(id);

        Assertions.assertEquals(expected, actual);
    }

    @DisplayName("Delete the user by invalid id, should throw EntityNotFoundException")
    @Test
    public void deleteById_InValidId_ShouldThrowEntityNotFoundException() {
        Long id = 1L;

        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> userRepository.deleteById(id));

        String expected = "There is no user by id: " + id;
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }
}