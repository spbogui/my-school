package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.ClassRoomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClassRoom and its DTO ClassRoomDTO.
 */
@Mapper(componentModel = "spring", uses = {LevelMapper.class})
public interface ClassRoomMapper extends EntityMapper<ClassRoomDTO, ClassRoom> {

    @Mapping(source = "level.id", target = "levelId")
    ClassRoomDTO toDto(ClassRoom classRoom);

    @Mapping(source = "levelId", target = "level")
    ClassRoom toEntity(ClassRoomDTO classRoomDTO);

    default ClassRoom fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(id);
        return classRoom;
    }
}
