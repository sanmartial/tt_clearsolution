package org.globaroman.clearsolution.shareObjects;

import java.time.LocalDate;
import org.globaroman.clearsolution.dto.AddressRequestDto;
import org.globaroman.clearsolution.dto.CreateUserRequestDto;
import org.globaroman.clearsolution.dto.SearchUserParameter;
import org.globaroman.clearsolution.dto.UpdatePhoneRequestDto;
import org.globaroman.clearsolution.dto.UserResponseDto;
import org.globaroman.clearsolution.model.Address;
import org.globaroman.clearsolution.model.User;

public class ObjectsForTest {

    public User getNewUser() {
        User user = new User();
        user.setPhone("+380671112233");
        user.setAddress(getAddress());
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setBirthDate(LocalDate.parse("1999-01-01"));
        user.setFirstName("Name");
        user.setLastName("LastName");
        return user;
    }

    public SearchUserParameter getSearchUserParameter() {
        SearchUserParameter searchUserParameter = new SearchUserParameter();
        searchUserParameter.setFromDate(LocalDate.parse("1970-01-01"));
        searchUserParameter.setToDate(LocalDate.parse("2004-01-01"));
        return searchUserParameter;
    }

    public UpdatePhoneRequestDto getUpdatePhoneDto() {
        UpdatePhoneRequestDto updatePhoneRequestDto = new UpdatePhoneRequestDto();
        updatePhoneRequestDto.setPhone("+380671112234");
        return updatePhoneRequestDto;
    }

    public UserResponseDto getUserResponseDto() {
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

    public CreateUserRequestDto getCreateUserRequestDto() {
        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        requestDto.setEmail("user@example.com");
        requestDto.setFirstName("Name");
        requestDto.setLastName("Lastname");
        requestDto.setPhone("+380671112233");
        requestDto.setBirthDate(LocalDate.parse("1999-01-01"));
        requestDto.setAddressRequestDto(getAddressDto());

        return requestDto;
    }

    public AddressRequestDto getAddressDto() {
        AddressRequestDto addressRequestDto = new AddressRequestDto();
        addressRequestDto.setCity("City");
        addressRequestDto.setStreet("Street");
        addressRequestDto.setBuilding("100");
        addressRequestDto.setApartment("15");
        addressRequestDto.setPostCode("23731");
        return addressRequestDto;
    }

    public Address getAddress() {
        Address addressRequestDto = new Address();
        addressRequestDto.setCity("City");
        addressRequestDto.setStreet("Street");
        addressRequestDto.setBuilding("100");
        addressRequestDto.setApartment("15");
        addressRequestDto.setPostCode("23731");
        return addressRequestDto;
    }

}
