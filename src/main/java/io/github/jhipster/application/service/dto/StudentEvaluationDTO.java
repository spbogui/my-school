package io.github.jhipster.application.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the StudentEvaluation entity.
 */
public class StudentEvaluationDTO implements Serializable {

    private Long id;

    @NotNull
    private Double grade;


    private Long actorId;

    private Long evaluationId;

    private Long evaluationModeId;

    private Long subjectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public Long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    public Long getEvaluationModeId() {
        return evaluationModeId;
    }

    public void setEvaluationModeId(Long evaluationModeId) {
        this.evaluationModeId = evaluationModeId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudentEvaluationDTO studentEvaluationDTO = (StudentEvaluationDTO) o;
        if (studentEvaluationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentEvaluationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentEvaluationDTO{" +
            "id=" + getId() +
            ", grade=" + getGrade() +
            ", actor=" + getActorId() +
            ", evaluation=" + getEvaluationId() +
            ", evaluationMode=" + getEvaluationModeId() +
            ", subject=" + getSubjectId() +
            "}";
    }
}
