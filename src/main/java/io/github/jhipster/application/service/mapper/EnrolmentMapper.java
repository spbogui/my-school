package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.EnrolmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Enrolment and its DTO EnrolmentDTO.
 */
@Mapper(componentModel = "spring", uses = {SchoolYearMapper.class, ActorMapper.class, ClassRoomMapper.class, RegimenMapper.class, SchoolMapper.class})
public interface EnrolmentMapper extends EntityMapper<EnrolmentDTO, Enrolment> {

    @Mapping(source = "schoolYear.id", target = "schoolYearId")
    @Mapping(source = "actor.id", target = "actorId")
    @Mapping(source = "classRoom.id", target = "classRoomId")
    @Mapping(source = "regimen.id", target = "regimenId")
    @Mapping(source = "previousSchool.id", target = "previousSchoolId")
    @Mapping(source = "previousClass.id", target = "previousClassId")
    EnrolmentDTO toDto(Enrolment enrolment);

    @Mapping(source = "schoolYearId", target = "schoolYear")
    @Mapping(source = "actorId", target = "actor")
    @Mapping(source = "classRoomId", target = "classRoom")
    @Mapping(source = "regimenId", target = "regimen")
    @Mapping(source = "previousSchoolId", target = "previousSchool")
    @Mapping(source = "previousClassId", target = "previousClass")
    Enrolment toEntity(EnrolmentDTO enrolmentDTO);

    default Enrolment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Enrolment enrolment = new Enrolment();
        enrolment.setId(id);
        return enrolment;
    }
}
