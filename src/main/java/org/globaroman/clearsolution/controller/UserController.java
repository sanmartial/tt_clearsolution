package org.globaroman.clearsolution.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.clearsolution.dto.AddressRequestDto;
import org.globaroman.clearsolution.dto.CreateUserRequestDto;
import org.globaroman.clearsolution.dto.SearchUserParameter;
import org.globaroman.clearsolution.dto.UpdatePhoneRequestDto;
import org.globaroman.clearsolution.dto.UserResponseDto;
import org.globaroman.clearsolution.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDto createNewUser(@RequestBody @Valid CreateUserRequestDto requestDto) {
        return userService.create(requestDto);
    }

    @PatchMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id,
                                      @Valid @RequestBody CreateUserRequestDto requestDto) {
        return userService.updateUser(id, requestDto);
    }

    @PatchMapping("/address/{id}")
    public UserResponseDto updateAddressUser(@PathVariable Long id,
                                             @Valid @RequestBody AddressRequestDto dto) {
        return userService.updateAddressUser(id, dto);
    }

    @PatchMapping("/phone/{id}")
    public UserResponseDto updatePhoneUser(@PathVariable Long id,
                                           @RequestBody @Valid UpdatePhoneRequestDto requestDto) {
        return userService.updatePhoneUser(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/search")
    public List<UserResponseDto> searchUserByBirthDate(
            @Valid SearchUserParameter searchUserParameter) {
        return userService.searchUsers(searchUserParameter);
    }
}
