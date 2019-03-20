package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ActorIdentifierNumberDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ActorIdentifierNumber and its DTO ActorIdentifierNumberDTO.
 */
@Mapper(componentModel = "spring", uses = {IdentifierTypeMapper.class, ActorMapper.class})
public interface ActorIdentifierNumberMapper extends EntityMapper<ActorIdentifierNumberDTO, ActorIdentifierNumber> {

    @Mapping(source = "identifierType.id", target = "identifierTypeId")
    @Mapping(source = "actor.id", target = "actorId")
    ActorIdentifierNumberDTO toDto(ActorIdentifierNumber actorIdentifierNumber);

    @Mapping(source = "identifierTypeId", target = "identifierType")
    @Mapping(source = "actorId", target = "actor")
    ActorIdentifierNumber toEntity(ActorIdentifierNumberDTO actorIdentifierNumberDTO);

    default ActorIdentifierNumber fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActorIdentifierNumber actorIdentifierNumber = new ActorIdentifierNumber();
        actorIdentifierNumber.setId(id);
        return actorIdentifierNumber;
    }
}
