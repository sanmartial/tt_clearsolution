package org.globaroman.clearsolution.mapper;

import org.globaroman.clearsolution.config.MapperConfig;
import org.globaroman.clearsolution.dto.AddressRequestDto;
import org.globaroman.clearsolution.model.Address;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface AddressMapper {

    Address toModel(AddressRequestDto requestDto);
}
