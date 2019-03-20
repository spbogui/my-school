package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ActorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Actor and its DTO ActorDTO.
 */
@Mapper(componentModel = "spring", uses = {CountryMapper.class})
public interface ActorMapper extends EntityMapper<ActorDTO, Actor> {

    @Mapping(source = "country.id", target = "countryId")
    ActorDTO toDto(Actor actor);

    @Mapping(source = "countryId", target = "country")
    Actor toEntity(ActorDTO actorDTO);

    default Actor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Actor actor = new Actor();
        actor.setId(id);
        return actor;
    }
}
