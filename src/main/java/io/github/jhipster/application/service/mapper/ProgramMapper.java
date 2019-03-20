package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ProgramDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Program and its DTO ProgramDTO.
 */
@Mapper(componentModel = "spring", uses = {SubjectMapper.class, ClassRoomMapper.class, RoomMapper.class, DaysMapper.class, SchoolYearMapper.class})
public interface ProgramMapper extends EntityMapper<ProgramDTO, Program> {

    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "classRoom.id", target = "classRoomId")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "days.id", target = "daysId")
    @Mapping(source = "schoolYear.id", target = "schoolYearId")
    ProgramDTO toDto(Program program);

    @Mapping(source = "subjectId", target = "subject")
    @Mapping(source = "classRoomId", target = "classRoom")
    @Mapping(source = "roomId", target = "room")
    @Mapping(source = "daysId", target = "days")
    @Mapping(source = "schoolYearId", target = "schoolYear")
    Program toEntity(ProgramDTO programDTO);

    default Program fromId(Long id) {
        if (id == null) {
            return null;
        }
        Program program = new Program();
        program.setId(id);
        return program;
    }
}
