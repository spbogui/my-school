package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.AskingPermissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AskingPermission and its DTO AskingPermissionDTO.
 */
@Mapper(componentModel = "spring", uses = {SchoolSchoolYearMapper.class, StudentMapper.class})
public interface AskingPermissionMapper extends EntityMapper<AskingPermissionDTO, AskingPermission> {

    @Mapping(source = "schoolSchoolYear.id", target = "schoolSchoolYearId")
    @Mapping(source = "student.id", target = "studentId")
    AskingPermissionDTO toDto(AskingPermission askingPermission);

    @Mapping(source = "schoolSchoolYearId", target = "schoolSchoolYear")
    @Mapping(source = "studentId", target = "student")
    AskingPermission toEntity(AskingPermissionDTO askingPermissionDTO);

    default AskingPermission fromId(Long id) {
        if (id == null) {
            return null;
        }
        AskingPermission askingPermission = new AskingPermission();
        askingPermission.setId(id);
        return askingPermission;
    }
}
