package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.TeacherSubjectSchoolYearDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TeacherSubjectSchoolYear and its DTO TeacherSubjectSchoolYearDTO.
 */
@Mapper(componentModel = "spring", uses = {ActorMapper.class, TeacherMapper.class, SchoolSchoolYearMapper.class})
public interface TeacherSubjectSchoolYearMapper extends EntityMapper<TeacherSubjectSchoolYearDTO, TeacherSubjectSchoolYear> {

    @Mapping(source = "actor.id", target = "actorId")
    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "schoolSchoolYear.id", target = "schoolSchoolYearId")
    TeacherSubjectSchoolYearDTO toDto(TeacherSubjectSchoolYear teacherSubjectSchoolYear);

    @Mapping(source = "actorId", target = "actor")
    @Mapping(source = "teacherId", target = "teacher")
    @Mapping(source = "schoolSchoolYearId", target = "schoolSchoolYear")
    TeacherSubjectSchoolYear toEntity(TeacherSubjectSchoolYearDTO teacherSubjectSchoolYearDTO);

    default TeacherSubjectSchoolYear fromId(Long id) {
        if (id == null) {
            return null;
        }
        TeacherSubjectSchoolYear teacherSubjectSchoolYear = new TeacherSubjectSchoolYear();
        teacherSubjectSchoolYear.setId(id);
        return teacherSubjectSchoolYear;
    }
}
