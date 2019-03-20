package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.LeaveHoliDayDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LeaveHoliDay and its DTO LeaveHoliDayDTO.
 */
@Mapper(componentModel = "spring", uses = {SchoolYearMapper.class})
public interface LeaveHoliDayMapper extends EntityMapper<LeaveHoliDayDTO, LeaveHoliDay> {

    @Mapping(source = "schoolYear.id", target = "schoolYearId")
    LeaveHoliDayDTO toDto(LeaveHoliDay leaveHoliDay);

    @Mapping(source = "schoolYearId", target = "schoolYear")
    LeaveHoliDay toEntity(LeaveHoliDayDTO leaveHoliDayDTO);

    default LeaveHoliDay fromId(Long id) {
        if (id == null) {
            return null;
        }
        LeaveHoliDay leaveHoliDay = new LeaveHoliDay();
        leaveHoliDay.setId(id);
        return leaveHoliDay;
    }
}
