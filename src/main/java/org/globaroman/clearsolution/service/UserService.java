package org.globaroman.clearsolution.service;

import jakarta.validation.Valid;
import java.util.List;
import org.globaroman.clearsolution.dto.AddressRequestDto;
import org.globaroman.clearsolution.dto.CreateUserRequestDto;
import org.globaroman.clearsolution.dto.SearchUserParameter;
import org.globaroman.clearsolution.dto.UpdatePhoneRequestDto;
import org.globaroman.clearsolution.dto.UserResponseDto;

public interface UserService {

    UserResponseDto create(CreateUserRequestDto requestDto);

    UserResponseDto updateUser(Long id, CreateUserRequestDto requestDto);

    UserResponseDto updateAddressUser(Long id, @Valid AddressRequestDto requestDto);

    UserResponseDto updatePhoneUser(Long id, UpdatePhoneRequestDto requestDto);

    void deleteUser(Long id);

    List<UserResponseDto> searchUsers(SearchUserParameter searchUserParameter);

}
