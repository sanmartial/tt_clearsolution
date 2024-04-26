package org.globaroman.clearsolution.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.globaroman.clearsolution.dto.AddressRequestDto;
import org.globaroman.clearsolution.dto.CreateUserRequestDto;
import org.globaroman.clearsolution.dto.SearchUserParameter;
import org.globaroman.clearsolution.dto.UpdatePhoneRequestDto;
import org.globaroman.clearsolution.dto.UserResponseDto;
import org.globaroman.clearsolution.exception.EntityNotFoundCustomException;
import org.globaroman.clearsolution.exception.RegistrationException;
import org.globaroman.clearsolution.mapper.AddressMapper;
import org.globaroman.clearsolution.mapper.UserMapper;
import org.globaroman.clearsolution.model.Address;
import org.globaroman.clearsolution.model.User;
import org.globaroman.clearsolution.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static Long userId = 1L;

    private final UserMapper userMapper;
    private final AddressMapper addressMapper;

    @Value("${USER.MIN.AGE}")
    private Long age;

    private List<User> usersRepository = new ArrayList<>();

    @Override
    public UserResponseDto create(CreateUserRequestDto requestDto) {

        User user = userMapper.toModel(requestDto);
        Address address = addressMapper.toModel(requestDto.getAddressRequestDto());

        user.setId(userId++);
        user.setAddress(address);

        LocalDate birthDate = getLocalDateFromString(requestDto.getBirthDate());

        if (isValidAge(birthDate)) {
            user.setBirthDate(birthDate);
            usersRepository.add(user);
        } else {
            throw new RegistrationException("The user has not yet reached the age of " + age);
        }

        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updateUser(Long id, CreateUserRequestDto requestDto) {
        User user = getUserFromRepository(id).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not found user with id: " + id)
        );
        usersRepository.remove(user);

        LocalDate date = getLocalDateFromString(requestDto.getBirthDate());
        User userUpdate = userMapper.toUpdate(requestDto, user);

        userUpdate.setBirthDate(date);

        usersRepository.add(userUpdate);

        return userMapper.toDto(userUpdate);
    }

    @Override
    public UserResponseDto updateAddressUser(Long id, AddressRequestDto requestDto) {
        User user = getUserFromRepository(id).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not found user with id: " + id)
        );
        usersRepository.remove(user);
        Address address = addressMapper.toModel(requestDto);
        user.setAddress(address);
        usersRepository.add(user);

        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updatePhoneUser(Long id, UpdatePhoneRequestDto requestDto) {
        User user = getUserFromRepository(id).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not found user with id: " + id)
        );
        usersRepository.remove(user);
        user.setPhone(requestDto.getPhone());
        usersRepository.add(user);

        return userMapper.toDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserFromRepository(id).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not found user with id: " + id)
        );
        usersRepository.remove(user);
    }

    @Override
    public List<UserResponseDto> searchUsers(SearchUserParameter searchUserParameter) {
        List<User> users = new ArrayList<>();

        LocalDate toDate = getLocalDateFromString(searchUserParameter.getToDate());
        LocalDate fromDate = getLocalDateFromString(searchUserParameter.getFromDate());

        for (User user : usersRepository) {
            if (!user.getBirthDate().isBefore(fromDate) && !user.getBirthDate().isAfter(toDate)) {
                users.add(user);
            }
        }

        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserResponseDto getUserById(Long id) {

        User user = getUserFromRepository(id).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not found user with id: " + id)
        );
        usersRepository.remove(user);
        usersRepository.add(user);

        return userMapper.toDto(user);
    }

    private boolean isValidAge(LocalDate birthDate) {
        Period ageDifference = Period.between(birthDate, LocalDate.now());
        return ageDifference.getYears() >= age;
    }

    private Optional<User> getUserFromRepository(Long id) {

        for (User user : usersRepository) {
            if (Objects.equals(user.getId(), id)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    private LocalDate getLocalDateFromString(String birthDate) {
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(birthDate, formatDate);
    }
}
