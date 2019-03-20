package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.PeriodDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Period and its DTO PeriodDTO.
 */
@Mapper(componentModel = "spring", uses = {PeriodTypeMapper.class, SchoolYearMapper.class})
public interface PeriodMapper extends EntityMapper<PeriodDTO, Period> {

    @Mapping(source = "periodLabel.id", target = "periodLabelId")
    @Mapping(source = "schoolYearlabel.id", target = "schoolYearlabelId")
    PeriodDTO toDto(Period period);

    @Mapping(source = "periodLabelId", target = "periodLabel")
    @Mapping(source = "schoolYearlabelId", target = "schoolYearlabel")
    Period toEntity(PeriodDTO periodDTO);

    default Period fromId(Long id) {
        if (id == null) {
            return null;
        }
        Period period = new Period();
        period.setId(id);
        return period;
    }
}
