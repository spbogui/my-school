package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.StudentEvaluationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StudentEvaluation and its DTO StudentEvaluationDTO.
 */
@Mapper(componentModel = "spring", uses = {ActorMapper.class, EvaluationMapper.class, EvaluationModeMapper.class, SubjectMapper.class})
public interface StudentEvaluationMapper extends EntityMapper<StudentEvaluationDTO, StudentEvaluation> {

    @Mapping(source = "actor.id", target = "actorId")
    @Mapping(source = "evaluation.id", target = "evaluationId")
    @Mapping(source = "evaluationMode.id", target = "evaluationModeId")
    @Mapping(source = "subject.id", target = "subjectId")
    StudentEvaluationDTO toDto(StudentEvaluation studentEvaluation);

    @Mapping(source = "actorId", target = "actor")
    @Mapping(source = "evaluationId", target = "evaluation")
    @Mapping(source = "evaluationModeId", target = "evaluationMode")
    @Mapping(source = "subjectId", target = "subject")
    StudentEvaluation toEntity(StudentEvaluationDTO studentEvaluationDTO);

    default StudentEvaluation fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentEvaluation studentEvaluation = new StudentEvaluation();
        studentEvaluation.setId(id);
        return studentEvaluation;
    }
}
