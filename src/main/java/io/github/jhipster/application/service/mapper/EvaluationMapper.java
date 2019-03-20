package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.EvaluationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Evaluation and its DTO EvaluationDTO.
 */
@Mapper(componentModel = "spring", uses = {EvaluationTypeMapper.class, SchoolSchoolYearMapper.class, PeriodMapper.class, ClassRoomMapper.class})
public interface EvaluationMapper extends EntityMapper<EvaluationDTO, Evaluation> {

    @Mapping(source = "evaluationType.id", target = "evaluationTypeId")
    @Mapping(source = "schoolSchoolYear.id", target = "schoolSchoolYearId")
    @Mapping(source = "period.id", target = "periodId")
    EvaluationDTO toDto(Evaluation evaluation);

    @Mapping(source = "evaluationTypeId", target = "evaluationType")
    @Mapping(source = "schoolSchoolYearId", target = "schoolSchoolYear")
    @Mapping(source = "periodId", target = "period")
    Evaluation toEntity(EvaluationDTO evaluationDTO);

    default Evaluation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Evaluation evaluation = new Evaluation();
        evaluation.setId(id);
        return evaluation;
    }
}
