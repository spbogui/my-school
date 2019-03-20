package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ResponsibleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Responsible and its DTO ResponsibleDTO.
 */
@Mapper(componentModel = "spring", uses = {ActorMapper.class})
public interface ResponsibleMapper extends EntityMapper<ResponsibleDTO, Responsible> {

    @Mapping(source = "actor.id", target = "actorId")
    ResponsibleDTO toDto(Responsible responsible);

    @Mapping(source = "actorId", target = "actor")
    Responsible toEntity(ResponsibleDTO responsibleDTO);

    default Responsible fromId(Long id) {
        if (id == null) {
            return null;
        }
        Responsible responsible = new Responsible();
        responsible.setId(id);
        return responsible;
    }
}
