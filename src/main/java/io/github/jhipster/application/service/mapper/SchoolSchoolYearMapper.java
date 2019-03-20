package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.SchoolSchoolYearDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SchoolSchoolYear and its DTO SchoolSchoolYearDTO.
 */
@Mapper(componentModel = "spring", uses = {SchoolMapper.class, SchoolYearMapper.class})
public interface SchoolSchoolYearMapper extends EntityMapper<SchoolSchoolYearDTO, SchoolSchoolYear> {

    @Mapping(source = "schoolName.id", target = "schoolNameId")
    @Mapping(source = "schoolYearlabel.id", target = "schoolYearlabelId")
    SchoolSchoolYearDTO toDto(SchoolSchoolYear schoolSchoolYear);

    @Mapping(source = "schoolNameId", target = "schoolName")
    @Mapping(source = "schoolYearlabelId", target = "schoolYearlabel")
    SchoolSchoolYear toEntity(SchoolSchoolYearDTO schoolSchoolYearDTO);

    default SchoolSchoolYear fromId(Long id) {
        if (id == null) {
            return null;
        }
        SchoolSchoolYear schoolSchoolYear = new SchoolSchoolYear();
        schoolSchoolYear.setId(id);
        return schoolSchoolYear;
    }
}
