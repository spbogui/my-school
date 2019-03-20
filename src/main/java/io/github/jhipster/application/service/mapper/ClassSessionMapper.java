package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ClassSessionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClassSession and its DTO ClassSessionDTO.
 */
@Mapper(componentModel = "spring", uses = {ClassSessionTypeMapper.class, ProgramMapper.class})
public interface ClassSessionMapper extends EntityMapper<ClassSessionDTO, ClassSession> {

    @Mapping(source = "classSessionType.id", target = "classSessionTypeId")
    @Mapping(source = "program.id", target = "programId")
    ClassSessionDTO toDto(ClassSession classSession);

    @Mapping(source = "classSessionTypeId", target = "classSessionType")
    @Mapping(source = "programId", target = "program")
    ClassSession toEntity(ClassSessionDTO classSessionDTO);

    default ClassSession fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassSession classSession = new ClassSession();
        classSession.setId(id);
        return classSession;
    }
}
