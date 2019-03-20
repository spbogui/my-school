package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.StudentDiplomaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StudentDiploma and its DTO StudentDiplomaDTO.
 */
@Mapper(componentModel = "spring", uses = {StudentMapper.class, DiplomaMapper.class, SchoolSchoolYearMapper.class})
public interface StudentDiplomaMapper extends EntityMapper<StudentDiplomaDTO, StudentDiploma> {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "diploma.id", target = "diplomaId")
    @Mapping(source = "schoolSchoolYear.id", target = "schoolSchoolYearId")
    StudentDiplomaDTO toDto(StudentDiploma studentDiploma);

    @Mapping(source = "studentId", target = "student")
    @Mapping(source = "diplomaId", target = "diploma")
    @Mapping(source = "schoolSchoolYearId", target = "schoolSchoolYear")
    StudentDiploma toEntity(StudentDiplomaDTO studentDiplomaDTO);

    default StudentDiploma fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentDiploma studentDiploma = new StudentDiploma();
        studentDiploma.setId(id);
        return studentDiploma;
    }
}
