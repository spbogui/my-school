package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ActorNameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ActorName and its DTO ActorNameDTO.
 */
@Mapper(componentModel = "spring", uses = {ActorMapper.class})
public interface ActorNameMapper extends EntityMapper<ActorNameDTO, ActorName> {

    @Mapping(source = "actor.id", target = "actorId")
    ActorNameDTO toDto(ActorName actorName);

    @Mapping(source = "actorId", target = "actor")
    ActorName toEntity(ActorNameDTO actorNameDTO);

    default ActorName fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActorName actorName = new ActorName();
        actorName.setId(id);
        return actorName;
    }
}
