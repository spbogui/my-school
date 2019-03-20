package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.TeacherMissingSessionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TeacherMissingSession and its DTO TeacherMissingSessionDTO.
 */
@Mapper(componentModel = "spring", uses = {ClassSessionMapper.class, TeacherMapper.class})
public interface TeacherMissingSessionMapper extends EntityMapper<TeacherMissingSessionDTO, TeacherMissingSession> {

    @Mapping(source = "classSession.id", target = "classSessionId")
    @Mapping(source = "student.id", target = "studentId")
    TeacherMissingSessionDTO toDto(TeacherMissingSession teacherMissingSession);

    @Mapping(source = "classSessionId", target = "classSession")
    @Mapping(source = "studentId", target = "student")
    TeacherMissingSession toEntity(TeacherMissingSessionDTO teacherMissingSessionDTO);

    default TeacherMissingSession fromId(Long id) {
        if (id == null) {
            return null;
        }
        TeacherMissingSession teacherMissingSession = new TeacherMissingSession();
        teacherMissingSession.setId(id);
        return teacherMissingSession;
    }
}
