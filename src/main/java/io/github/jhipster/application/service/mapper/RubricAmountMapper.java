package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.RubricAmountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RubricAmount and its DTO RubricAmountDTO.
 */
@Mapper(componentModel = "spring", uses = {RubricMapper.class, LevelMapper.class, SchoolYearMapper.class})
public interface RubricAmountMapper extends EntityMapper<RubricAmountDTO, RubricAmount> {

    @Mapping(source = "rubric.id", target = "rubricId")
    @Mapping(source = "level.id", target = "levelId")
    @Mapping(source = "schoolYear.id", target = "schoolYearId")
    RubricAmountDTO toDto(RubricAmount rubricAmount);

    @Mapping(source = "rubricId", target = "rubric")
    @Mapping(source = "levelId", target = "level")
    @Mapping(source = "schoolYearId", target = "schoolYear")
    RubricAmount toEntity(RubricAmountDTO rubricAmountDTO);

    default RubricAmount fromId(Long id) {
        if (id == null) {
            return null;
        }
        RubricAmount rubricAmount = new RubricAmount();
        rubricAmount.setId(id);
        return rubricAmount;
    }
}
