package com.azry.dmtp.fiintegration.mapper;

import com.azry.dmtp.model.ParticipantModel;
import com.azry.dmtp.z.bankservice.ClientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ParticipantAccountMapper.class})
public interface ParticipantMapper {

    @Mapping(target = "accounts", source = "accountDTOList")
    @Mapping(target = "mobilePhone", source = "phone")
    @Mapping(target = "birthCountry", source = "citizenship")
    @Mapping(target = "country", source = "citizenship")
    ParticipantModel clientToParticipant(ClientDTO clientDTO);
}