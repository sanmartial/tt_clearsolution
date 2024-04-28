package org.globaroman.clearsolution.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
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
import org.globaroman.clearsolution.repository.UserRepository;
import org.globaroman.clearsolution.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static Long userId = 1L;

    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    @Value("${USER.MIN.AGE}")
    private Long limitAge;

    @Override
    public UserResponseDto create(CreateUserRequestDto requestDto) {

        User user = userMapper.toModel(requestDto);
        Address address = addressMapper.toModel(requestDto.getAddressRequestDto());

        user.setId(userId++);
        user.setAddress(address);

        if (isValidAge(requestDto.getBirthDate())) {
            return userMapper.toDto(userRepository.save(user));
        } else {
            throw new RegistrationException("The user has not yet reached the age of " + limitAge);
        }
    }

    @Override
    public UserResponseDto updateUser(Long id, CreateUserRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not found user with id: " + id)
        );

        User userUpdate = userMapper.toUpdate(requestDto, user);

        return userMapper.toDto(userUpdate);
    }

    @Override
    public UserResponseDto updateAddressUser(Long id, AddressRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not found user with id: " + id)
        );

        Address address = addressMapper.toModel(requestDto);
        user.setAddress(address);

        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updatePhoneUser(Long id, UpdatePhoneRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not found user with id: " + id)
        );

        user.setPhone(requestDto.getPhone());

        return userMapper.toDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponseDto> searchUsers(SearchUserParameter searchUserParameter) {

        List<User> users = new ArrayList<>();
        List<User> usersFromRepo = userRepository.findAll();

        for (User user : usersFromRepo) {
            if (!user.getBirthDate().isBefore(searchUserParameter.getFromDate())
                    && !user.getBirthDate().isAfter(searchUserParameter.getToDate())) {
                users.add(user);
            }
        }

        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    private boolean isValidAge(LocalDate birthDate) {
        Period ageDifference = Period.between(birthDate, LocalDate.now());
        return ageDifference.getYears() >= limitAge;
    }

}
