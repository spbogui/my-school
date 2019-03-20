package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ActorRelationshipDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ActorRelationship and its DTO ActorRelationshipDTO.
 */
@Mapper(componentModel = "spring", uses = {ResponsibleMapper.class, StudentMapper.class, RelationshipTypeMapper.class})
public interface ActorRelationshipMapper extends EntityMapper<ActorRelationshipDTO, ActorRelationship> {

    @Mapping(source = "responsible.id", target = "responsibleId")
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "relationshipType.id", target = "relationshipTypeId")
    ActorRelationshipDTO toDto(ActorRelationship actorRelationship);

    @Mapping(source = "responsibleId", target = "responsible")
    @Mapping(source = "studentId", target = "student")
    @Mapping(source = "relationshipTypeId", target = "relationshipType")
    ActorRelationship toEntity(ActorRelationshipDTO actorRelationshipDTO);

    default ActorRelationship fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActorRelationship actorRelationship = new ActorRelationship();
        actorRelationship.setId(id);
        return actorRelationship;
    }
}
