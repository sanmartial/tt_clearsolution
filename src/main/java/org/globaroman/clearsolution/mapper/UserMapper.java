package org.globaroman.clearsolution.mapper;

import org.globaroman.clearsolution.config.MapperConfig;
import org.globaroman.clearsolution.dto.CreateUserRequestDto;
import org.globaroman.clearsolution.dto.UserResponseDto;
import org.globaroman.clearsolution.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    UserResponseDto toDto(User user);

    User toModel(CreateUserRequestDto requestDto);

    User toUpdate(CreateUserRequestDto requestDto, @MappingTarget User user);
}
