package io.github.jhipster.application.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Evaluation entity.
 */
public class EvaluationDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate plannedDate;

    private Boolean isDone;

    private LocalDate evaluationDate;

    private Integer duration;


    private Long evaluationTypeId;

    private Long schoolSchoolYearId;

    private Long periodId;

    private Set<ClassRoomDTO> classRooms = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(LocalDate plannedDate) {
        this.plannedDate = plannedDate;
    }

    public Boolean isIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDate evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getEvaluationTypeId() {
        return evaluationTypeId;
    }

    public void setEvaluationTypeId(Long evaluationTypeId) {
        this.evaluationTypeId = evaluationTypeId;
    }

    public Long getSchoolSchoolYearId() {
        return schoolSchoolYearId;
    }

    public void setSchoolSchoolYearId(Long schoolSchoolYearId) {
        this.schoolSchoolYearId = schoolSchoolYearId;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public Set<ClassRoomDTO> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(Set<ClassRoomDTO> classRooms) {
        this.classRooms = classRooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EvaluationDTO evaluationDTO = (EvaluationDTO) o;
        if (evaluationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), evaluationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EvaluationDTO{" +
            "id=" + getId() +
            ", plannedDate='" + getPlannedDate() + "'" +
            ", isDone='" + isIsDone() + "'" +
            ", evaluationDate='" + getEvaluationDate() + "'" +
            ", duration=" + getDuration() +
            ", evaluationType=" + getEvaluationTypeId() +
            ", schoolSchoolYear=" + getSchoolSchoolYearId() +
            ", period=" + getPeriodId() +
            "}";
    }
}
