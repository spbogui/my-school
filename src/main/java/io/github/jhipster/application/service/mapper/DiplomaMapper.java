package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.DiplomaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Diploma and its DTO DiplomaDTO.
 */
@Mapper(componentModel = "spring", uses = {CycleMapper.class})
public interface DiplomaMapper extends EntityMapper<DiplomaDTO, Diploma> {

    @Mapping(source = "cycle.id", target = "cycleId")
    @Mapping(source = "parentDiploma.id", target = "parentDiplomaId")
    DiplomaDTO toDto(Diploma diploma);

    @Mapping(source = "cycleId", target = "cycle")
    @Mapping(source = "parentDiplomaId", target = "parentDiploma")
    Diploma toEntity(DiplomaDTO diplomaDTO);

    default Diploma fromId(Long id) {
        if (id == null) {
            return null;
        }
        Diploma diploma = new Diploma();
        diploma.setId(id);
        return diploma;
    }
}
