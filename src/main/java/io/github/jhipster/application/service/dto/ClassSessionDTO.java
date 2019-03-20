package io.github.jhipster.application.service.dto;
import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ClassSession entity.
 */
public class ClassSessionDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant startHour;

    @NotNull
    private Instant endHour;

    @NotNull
    private String detail;

    @NotNull
    private LocalDate createdAt;


    private Long classSessionTypeId;

    private Long programId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartHour() {
        return startHour;
    }

    public void setStartHour(Instant startHour) {
        this.startHour = startHour;
    }

    public Instant getEndHour() {
        return endHour;
    }

    public void setEndHour(Instant endHour) {
        this.endHour = endHour;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Long getClassSessionTypeId() {
        return classSessionTypeId;
    }

    public void setClassSessionTypeId(Long classSessionTypeId) {
        this.classSessionTypeId = classSessionTypeId;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassSessionDTO classSessionDTO = (ClassSessionDTO) o;
        if (classSessionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classSessionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassSessionDTO{" +
            "id=" + getId() +
            ", startHour='" + getStartHour() + "'" +
            ", endHour='" + getEndHour() + "'" +
            ", detail='" + getDetail() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", classSessionType=" + getClassSessionTypeId() +
            ", program=" + getProgramId() +
            "}";
    }
}
