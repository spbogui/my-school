package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.StudentMissingSessionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StudentMissingSession and its DTO StudentMissingSessionDTO.
 */
@Mapper(componentModel = "spring", uses = {ClassSessionMapper.class, StudentMapper.class})
public interface StudentMissingSessionMapper extends EntityMapper<StudentMissingSessionDTO, StudentMissingSession> {

    @Mapping(source = "classSession.id", target = "classSessionId")
    @Mapping(source = "student.id", target = "studentId")
    StudentMissingSessionDTO toDto(StudentMissingSession studentMissingSession);

    @Mapping(source = "classSessionId", target = "classSession")
    @Mapping(source = "studentId", target = "student")
    StudentMissingSession toEntity(StudentMissingSessionDTO studentMissingSessionDTO);

    default StudentMissingSession fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentMissingSession studentMissingSession = new StudentMissingSession();
        studentMissingSession.setId(id);
        return studentMissingSession;
    }
}
