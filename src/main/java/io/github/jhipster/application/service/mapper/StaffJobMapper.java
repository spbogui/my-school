package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.StaffJobDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StaffJob and its DTO StaffJobDTO.
 */
@Mapper(componentModel = "spring", uses = {StaffMapper.class, JobMapper.class})
public interface StaffJobMapper extends EntityMapper<StaffJobDTO, StaffJob> {

    @Mapping(source = "staff.id", target = "staffId")
    @Mapping(source = "job.id", target = "jobId")
    StaffJobDTO toDto(StaffJob staffJob);

    @Mapping(source = "staffId", target = "staff")
    @Mapping(source = "jobId", target = "job")
    StaffJob toEntity(StaffJobDTO staffJobDTO);

    default StaffJob fromId(Long id) {
        if (id == null) {
            return null;
        }
        StaffJob staffJob = new StaffJob();
        staffJob.setId(id);
        return staffJob;
    }
}
