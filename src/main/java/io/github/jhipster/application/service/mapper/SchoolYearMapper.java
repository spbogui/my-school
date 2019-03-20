package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.SchoolYearDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SchoolYear and its DTO SchoolYearDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchoolYearMapper extends EntityMapper<SchoolYearDTO, SchoolYear> {



    default SchoolYear fromId(Long id) {
        if (id == null) {
            return null;
        }
        SchoolYear schoolYear = new SchoolYear();
        schoolYear.setId(id);
        return schoolYear;
    }
}
